package org.apache.geode.example.debs.listeners;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.query.CqEvent;
import com.gemstone.gemfire.cache.query.CqListener;

import java.util.Properties;

/**
 * @author wmarkito
 * @date 1/29/16.
 */
public class EmtpyTaxiListener implements CqListener, Declarable {

  @Override
  public void onEvent(CqEvent aCqEvent) {

  }

  @Override
  public void onError(CqEvent aCqEvent) {

  }

  @Override
  public void close() {

  }

  @Override
  public void init(Properties props) {

  }
}
