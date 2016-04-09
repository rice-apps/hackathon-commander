package org.riceapps.hackathon.structs;

public enum Gender {
  FEMALE("Female"),
  MALE("Male");
  
  private final String humanReadableString;
  
  Gender(String humanReadableString) {
    this.humanReadableString = humanReadableString;
  }
  
  @Override
  public String toString() {
    return humanReadableString;
  }
  
  public int toInt() {
    return this.ordinal();
  }
  
  public static Gender fromInt(int i) {
    if (i < 0 || i >= Gender.values().length) {
      throw new IllegalArgumentException();
    }
    
    return Gender.values()[i];
  }
}
