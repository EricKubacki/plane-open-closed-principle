package main;

public interface Crew {
  boolean isValid();

  class NullCrew implements Crew {
    @Override
    public boolean isValid() {
      return false;
    }

    @Override
    public String toString() {
      return "crew has not been set";
    }
  }
}
