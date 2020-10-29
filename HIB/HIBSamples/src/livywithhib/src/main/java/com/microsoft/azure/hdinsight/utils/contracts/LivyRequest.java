package com.microsoft.azure.hdinsight.utils;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/*
 * Class for deserializing the livy request.
*/
public class LivyRequest {
	
  @SerializedName(value = "file")
  private String FileName;
  
  @SerializedName(value = "className")
  private String ClassName;
  
  @SerializedName(value = "name")
  private String Name;

  @SerializedName(value = "files")
  private ArrayList<String> Files;
  
  @SerializedName(value = "args")
  private ArrayList<String> Arguments;
  
  @SerializedName(value = "conf")
  private LivyPayloadConf Configuration;

  public LivyRequest() {
    super();
  }

  public LivyRequest(
      String fileName, 
      String className) {
    super();

    FileName = fileName;
    ClassName = className;
  }

  public String getFileName() {
    return FileName;
  }

  public void setFileName(String fileName) {
    FileName = fileName;
  }

  public String getClassName() {
    return ClassName;
  }
  
  public void setClassName(String className) {
    ClassName = className;
  }

  public String getName() {
    return ClassName;
  }

  public void setName(String name) {
    Name = name;
  }

  public ArrayList<String> getFiles() {
    return Files;
  }

  public void setFiles(ArrayList<String> files) {
    Files = files;
  }

  public ArrayList<String> getArguments() {
    return Arguments;
  }

  public void setArguments(ArrayList<String> arguments) {
	Arguments = arguments;
  }

  public LivyPayloadConf getConfiguration() {
    return Configuration;
  }

  public void setConfiguration(LivyPayloadConf configuration) {
	  Configuration = configuration;
  }  
}
