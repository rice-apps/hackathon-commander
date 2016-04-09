package org.riceapps.hackathon.structs;

public enum SchoolYear {
  HIGH_SCHOOL("In High School"),
  FRESHMAN("Freshman"),
  SOPHOMORE("Sophomore"),
  JUNIOR("Junior"),
  SENIOR("Senior"),
  GRADUATE("Graduate Student"),
  POST_GRADUATE("Post-Graduate Student");
  
  private final String humanReadableString;
  
  SchoolYear(String humanReadableString) {
    this.humanReadableString = humanReadableString;
  }
  
  @Override
  public String toString() {
    return humanReadableString;
  }
  
  public int toInt() {
    return this.ordinal();
  }
  
  public static SchoolYear fromInt(int i) {
    if (i < 0 || i >= SchoolYear.values().length) {
      throw new IllegalArgumentException();
    }
    
    return SchoolYear.values()[i];
  }
}
