package main.eric;

import java.util.ArrayList;
import java.util.List;

import main.Crew;
import main.Crew.NullCrew;
import main.Plane;
import main.exception.CannotTakeOffException;

public class PassengerPlane implements Plane {

  private static final List<TakeOffCheck> takeOffCheckList = new ArrayList<TakeOffCheck>();
  private TakeOffPlan plan;

  static {
    takeOffCheckList.add(new HangerCheck());
    takeOffCheckList.add(new CrewCheck());
    takeOffCheckList.add(new LowFuelCheck());
    takeOffCheckList.add(new FuelCheck());    
  }

  public PassengerPlane(PlaneStatus status) {
    plan = new TakeOffPlan();
    plan.status = status;
  }

  @Override
  public PlaneStatus getPlaneStatus() {
    return plan.status;
  }

  @Override
  public void takeOff() {
    if(PlaneStatus.READY_FOR_TAKEOFF.equals(plan.status) ||
       PlaneStatus.FUEL_WARNING.equals(plan.status)) {
      plan.status = PlaneStatus.IN_FLIGHT;
      return;
    }
    throw new CannotTakeOffException("Plane is not ready to take off");
  }

  @Override
  public PlaneStatus prepareForTakeOff() {
    for(TakeOffCheck check : takeOffCheckList) {
      if(!check.checksOut(plan)) {
        plan.status = check.getStatus();
        return plan.status;
      }
    }
    plan.status = PlaneStatus.READY_FOR_TAKEOFF;
    return plan.status;
  }

  public void setCrew(Crew crew) {
    plan.crew = crew;
  }

  public void setStatus(PlaneStatus status) {
    plan.status = status;
  }

  public void setFuel(int precentage) {
    plan.fuelLevel = precentage;
  }

  public void setFuelingBeforeTakeOff(boolean willFuel) {
    plan.fuelingBeforeTakeOff = willFuel;
  }

  interface TakeOffCheck {
    boolean checksOut(TakeOffPlan plan);
    PlaneStatus getStatus();
  }

  private static class TakeOffPlan {
    PlaneStatus status;
    Crew crew = new NullCrew();
    int fuelLevel = 0;
    boolean fuelingBeforeTakeOff = false;
  }

  private static class HangerCheck implements TakeOffCheck {
    TakeOffPlan plan;
    @Override
    public boolean checksOut(TakeOffPlan plan) {
      this.plan = plan;
      return PlaneStatus.IN_HANGER.equals(plan.status);
    }

    @Override
    public PlaneStatus getStatus() {
      return plan.status;
    }
  }
  private static class CrewCheck implements TakeOffCheck {

    @Override
    public boolean checksOut(TakeOffPlan plan) {
      return plan.crew.isValid();
    }

    @Override
    public PlaneStatus getStatus() {
      return PlaneStatus.NEED_CREW;
    }
  }

  private static class LowFuelCheck implements TakeOffCheck {

    @Override
    public boolean checksOut(TakeOffPlan plan) {
      return !(plan.fuelLevel > 25 && plan.fuelLevel < 75);
    }

    @Override
    public PlaneStatus getStatus() {
      return PlaneStatus.FUEL_WARNING;
    }
  }

  private static class FuelCheck implements TakeOffCheck {

    @Override
    public boolean checksOut(TakeOffPlan plan) {
      return (plan.fuelLevel >= 75 || plan.fuelingBeforeTakeOff);
    }

    @Override
    public PlaneStatus getStatus() {
      return PlaneStatus.NEED_FUEL;
    }
  }
}
