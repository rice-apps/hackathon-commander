package org.riceapps.hackathon.structs;

public enum TravelMethod {
  PLANE,
  TRAIN,
  BUS,
  CAR,
  BIKE,
  WALK,
  OTHER;
  
  public int toInt() {
    return this.ordinal();
  }
  
  public static TravelMethod fromInt(int i) {
    if (i < 0 || i >= TravelMethod.values().length) {
      throw new IllegalArgumentException();
    }
    
    return TravelMethod.values()[i];
  }
}
