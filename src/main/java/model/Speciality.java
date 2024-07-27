package model;

import java.util.HashSet;

public enum Speciality {
    CARDIOLOGIST, DERMATOLOGIST, ORTHOPEDIC, GENERAL_PHYSICIAN;
    private static HashSet<String> specialitySet = new HashSet<>();
    static{
          for(Speciality speciality : Speciality.values()){
              specialitySet.add(String.valueOf(speciality));
          }
    }

    public static boolean contains(String speciality){
        return specialitySet.contains(speciality.toUpperCase());
    }
}