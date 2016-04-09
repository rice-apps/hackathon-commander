package org.riceapps.hackathon.structs;

public enum Privilege {
  REVIEWER;
  
  public int toInt() {
    return this.ordinal() + 1;
  }
  
  public static Privilege fromInt(int value) {
    Privilege[] values = Privilege.values();
    
    if (value <= 0 || value > values.length) {
      throw new IllegalArgumentException();
    }
    
    return values[value - 1];
  }
}
