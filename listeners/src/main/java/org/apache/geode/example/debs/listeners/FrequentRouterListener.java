package org.apache.geode.example.debs.listeners;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEvent;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEventListener;

import org.apache.logging.log4j.LogManager;

import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.Logger;

/**
 * create async-event-queue --id=frequentRouterQueue --listener=org.apache.geode.example.debs.listeners.FrequentRouterListener
 * @author wmarkito
 * @date 11/4/15.
 */
public class FrequentRouterListener implements AsyncEventListener, Declarable {

  private static final Logger logger = LogManager.getLogger("org.apache.geode.example.debs.listeners.FrequentRouterListener");

  @Override
  public boolean processEvents(List<AsyncEvent> list) {

    list.forEach(e -> logger.info(e.getSerializedValue().toString()) );

    list.clear();

    return true;
  }

  @Override
  public void close() {

  }

  @Override
  public void init(Properties props) {

  }
}
