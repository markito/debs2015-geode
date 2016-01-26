package org.apache.geode.example.debs.listeners;

import com.gemstone.gemfire.cache.execute.Function;
import com.gemstone.gemfire.cache.execute.FunctionContext;

/**
 * Created by sbawaskar on 1/22/16.
 */
public class ProfitableAreasFunction implements Function {

  @Override
  public boolean hasResult() {
    return false;
  }

  @Override
  public void execute(FunctionContext context) {

  }

  @Override
  public String getId() {
    return null;
  }

  @Override
  public boolean optimizeForWrite() {
    return false;
  }

  @Override
  public boolean isHA() {
    return true;
  }
}
