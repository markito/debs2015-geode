package org.apache.geode.example.debs.client;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.client.ClientRegionShortcut;
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

  public void registerInterest() {
    Region frequentRoutesRegion = getFrequentRoutesRegion();
    frequentRoutesRegion.registerInterestRegex(".*");
  }
}
