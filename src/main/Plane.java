package main;

public interface Plane {
  enum PlaneStatus {
    IN_FLIGHT, READY_FOR_TAKEOFF, IN_HANGER, FUEL_WARNING, NEED_FUEL, NEED_CREW
  }

  PlaneStatus getPlaneStatus();
  void takeOff();
  PlaneStatus prepareForTakeOff();
}
