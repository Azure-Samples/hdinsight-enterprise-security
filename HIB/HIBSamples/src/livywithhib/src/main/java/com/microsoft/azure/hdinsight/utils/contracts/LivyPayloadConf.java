package com.microsoft.azure.hdinsight.utils;

import com.google.gson.annotations.SerializedName;

/*
 * Class for deserializing the livy payload configuration.
*/
public class LivyPayloadConf {
	
  @SerializedName(value = "numExecutors")
  private String NumExecutors;
  
  @SerializedName(value = "driverMemory")
  private String DriverMemory;
  
  @SerializedName(value = "executorMemory")
  private String ExecutorMemory;

  @SerializedName(value = "executorCores")
  private String ExecutorCores;
  
  @SerializedName(value = "spark.yarn.maxAppAttempts")
  private String MaxAppAttempts;
  
  public LivyPayloadConf() {
    super();
  }

  public String getNumExecutors() {
    return NumExecutors;
  }

  public void setNumExecutors(String numExecutors) {
	  NumExecutors = numExecutors;
  }

  public String getDriverMemory() {
    return DriverMemory;
  }
  
  public void setDriverMemory(String driverMemory) {
	DriverMemory = driverMemory;
  }

  public String getExecutorMemory() {
    return ExecutorMemory;
  }

  public void setExecutorMemory(String executorMemory) {
	  ExecutorMemory = executorMemory;
  }

  public String getExecutorCores() {
    return ExecutorCores;
  }

  public void setFiles(String executorCores) {
	  ExecutorCores = executorCores;
  }

  public String getMaxAppAttempts() {
    return MaxAppAttempts;
  }

  public void setMaxAppAttempts(String maxAppAttempts) {
	  MaxAppAttempts = maxAppAttempts;
  }
  
}
