package org.apache.geode.example.debs.listeners;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEvent;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEventListener;

import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * create async-event-queue --id=cellGridListener --listener=org.apache.geode.example.debs.listeners.CellGridListener
 * @author wmarkito
 * @date 11/4/15.
 */
public class CellGridListener implements AsyncEventListener, Declarable {

  Logger logger = Logger.getLogger("org.apache.geode.example.debs.listeners.CellGridListener");

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
