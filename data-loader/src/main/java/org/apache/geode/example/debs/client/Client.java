package org.apache.geode.example.debs.client;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.client.ClientRegionFactory;
import com.gemstone.gemfire.cache.client.ClientRegionShortcut;
import com.gemstone.gemfire.cache.query.*;
import org.apache.geode.example.debs.config.Config;

import java.util.logging.Logger;

/**
 * Created by sbawaskar on 1/21/16.
 */
public class Client {

  private static final Logger logger = Logger.getLogger(Client.class.getName());
  private ClientCache clientCache;
  private Region frequentRoutesRegion;

  private void connect() {
    this.clientCache = new ClientCacheFactory()
        .addPoolLocator(Config.LOCATOR_HOST, Config.LOCATOR_PORT)
        .setPoolSubscriptionEnabled(true)
        .create();
  }
  private Region getFrequentRoutesRegion() {
    if (this.frequentRoutesRegion == null) {
      FrequentRoutesListener listener = new FrequentRoutesListener(clientCache);
      ClientRegionFactory clientRegionFactory = this.clientCache.createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY);
      clientRegionFactory.addCacheListener(listener);
      this.frequentRoutesRegion = clientRegionFactory.create(Config.FREQUENT_ROUTES_REGION);
      logger.info("Region created:"+this.frequentRoutesRegion);
      listener.registerLocalQuery();
    }
    return this.frequentRoutesRegion;
  }


  public void registerInterest() {
    Region frequentRoutes = getFrequentRoutesRegion();
    frequentRoutes.registerInterestRegex(".*");
  }

  public void registerCQ() throws Exception {

    getFrequentRoutesRegion();
    CqAttributesFactory cqf = new CqAttributesFactory();
    CqListener frequentRoutesListener = new FrequentRoutesCQListener(clientCache);
    cqf.addCqListener(frequentRoutesListener);
    CqAttributes cqa = cqf.create();
    String cqName = "frequentRoutes";
    String queryStr = "SELECT * FROM /FrequentRoute";

    logger.info("registering CQ ...");
    CqQuery priceTracker = this.clientCache.getQueryService().newCq(cqName, queryStr, cqa);
    try {
      SelectResults sResults = priceTracker.executeWithInitialResults();
      for (Object o : sResults) {
        Struct s = (Struct) o;
        StringBuilder sb = new StringBuilder();
        for (Object field : s.getFieldValues()) {
          sb.append(" ").append(field);
        }
        logger.info(sb.toString());
      }
    } catch (RegionNotFoundException e) {
      logger.warning(e.getLocalizedMessage());
      throw e;
    }
    logger.info("CQ registered");
  }

  public static void main(String[] args) {
    Client client = new Client();
    try {
      client.connect();
      //client.registerCQ();
      client.registerInterest();
      Thread.sleep(1000 * 1000);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
