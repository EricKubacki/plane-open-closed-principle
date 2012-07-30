package main;

import main.Crew.NullCrew;
import main.exception.CannotTakeOffException;

public class PassengerPlane implements Plane {

  PlaneStatus status;
  Crew crew = new NullCrew();
  int fuelLevel = 0;
  boolean fuelingBeforeTakeOff = false;
  boolean preparedToTakeOff = false;

  public PassengerPlane (PlaneStatus status) {
    this.status = status;
  }

  @Override
  public PlaneStatus getPlaneStatus() {
    return status;
  }

  @Override
  public PlaneStatus prepareForTakeOff() {
    PlaneStatus returnStatus;
    if (PlaneStatus.IN_HANGER.equals(status)) {
      if (crew.isValid()) {
        if(fuelLevel > 25 && fuelLevel < 75) {        
          returnStatus = PlaneStatus.FUEL_WARNING;
          preparedToTakeOff = true;
        } else if(fuelLevel >= 75 || fuelingBeforeTakeOff) {
          returnStatus = PlaneStatus.READY_FOR_TAKEOFF;
          preparedToTakeOff = true;
        } else {
          returnStatus = PlaneStatus.NEED_FUEL;
        }
      } else {
        returnStatus = PlaneStatus.NEED_CREW;
      }
    } else {
      returnStatus = status;
    }
    return returnStatus;
  }

  @Override
  public void takeOff() {
    if(preparedToTakeOff) {
      status = PlaneStatus.IN_FLIGHT;
      return;
    }
    throw new CannotTakeOffException("Plane is not ready to take off");
  }

  public void setCrew(Crew crew) {
    this.crew = crew;
  }

  public void setStatus(PlaneStatus status) {
    this.status = status;
  }

  public void setFuel(int precentage) {
    fuelLevel = precentage;
  }

  public void setFuelingBeforeTakeOff(boolean willFuel) {
    fuelingBeforeTakeOff = willFuel;
  }

}
