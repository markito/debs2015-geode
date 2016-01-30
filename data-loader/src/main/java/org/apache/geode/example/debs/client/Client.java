package org.apache.geode.example.debs.client;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.client.ClientRegionShortcut;
import com.gemstone.gemfire.cache.query.*;
import org.apache.geode.example.debs.config.Config;
import org.apache.log4j.Logger;

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
      this.frequentRoutesRegion = this.clientCache.createClientRegionFactory(ClientRegionShortcut.PROXY).create(Config.FREQUENT_ROUTES_REGION);
    }
    return this.frequentRoutesRegion;
  }


  public void registerCQ() throws CqException, CqExistsException {

    getFrequentRoutesRegion();
    CqAttributesFactory cqf = new CqAttributesFactory();
    CqListener frequentRoutesListener = new FrequentRoutesListener();
    cqf.addCqListener(frequentRoutesListener);
    CqAttributes cqa = cqf.create();
    String cqName = "frequentRoutes";
    String queryStr = "SELECT * FROM /FrequentRoute";

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
      logger.warn(e);
    }
  }

  public static void main(String[] args) {
    Client client = new Client();
    try {
      client.connect();
      client.registerCQ();
      Thread.sleep(1000 * 1000);
    } catch (CqException | CqExistsException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
