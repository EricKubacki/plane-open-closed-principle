package test;

import static main.Plane.PlaneStatus.IN_HANGER;
import static main.Plane.PlaneStatus.NEED_CREW;
import static main.Plane.PlaneStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import main.Crew;
import main.PassengerPlane;
import main.exception.CannotTakeOffException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PassengerPlaneTest {

  private PassengerPlane plane;
  @Mock private Crew crew;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    plane = getWellPreparedPlaneForTakeOff();
  }

  @Test
  public void testGetPlaneStatus() {
    PassengerPlane plane = new PassengerPlane(IN_HANGER);
    assertThat(plane.getPlaneStatus(), is(equalTo(IN_HANGER)));
  }

  @Test
  public void testPrepareForTakeOff_withPlaneInFight_shouldReturnThatStatus(){
    plane = getWellPreparedPlaneForTakeOff();
    plane.setStatus(IN_FLIGHT);

    assertThat(plane.prepareForTakeOff(), is(equalTo(IN_FLIGHT)));
  }

  @Test
  public void testPrepareForTakeOff_withNoCrew_shouldNeedCrew() {
    when(crew.isValid()).thenReturn(false);
    plane.setCrew(crew);
    assertThat(plane.prepareForTakeOff(),is(equalTo(NEED_CREW)));
  }

  @Test
  public void testPrepareForTakeOff_withValidCrew_shouldBeReadyForTakeOff() {
    when(crew.isValid()).thenReturn(true);
    plane.setCrew(crew);
    assertThat(plane.prepareForTakeOff(),is(equalTo(READY_FOR_TAKEOFF)));
  }

  @Test
  public void testPrepareForTakeOff_withNoFuel_shouldNeedFuel() {
    plane.setFuel(0);
    assertThat(plane.prepareForTakeOff(),is(equalTo(NEED_FUEL)));
  }

  @Test
  public void testPrepareForTakeOff_withVeryLowFuel_shouldFuelWarning() {
    plane.setFuel(26);
    assertThat(plane.prepareForTakeOff(),is(equalTo(FUEL_WARNING)));
  }

  @Test
  public void testPrepareForTakeOff_withLowFuel_shouldFuelWarning() {
    plane.setFuel(74);
    assertThat(plane.prepareForTakeOff(),is(equalTo(FUEL_WARNING)));
  }

  @Test
  public void testPrepareForTakeOff_withLotsOfFuel_shouldReadyForTakeOff() {
    plane.setFuel(75);
    assertThat(plane.prepareForTakeOff(),is(equalTo(READY_FOR_TAKEOFF)));
  }

  @Test
  public void testPrepareForTakeOff_withNoFuelButWillFillBeforeTakeOff_shouldReadyForTakeOff() {
    plane.setFuel(0);
    plane.setFuelingBeforeTakeOff(true);
    assertThat(plane.prepareForTakeOff(),is(equalTo(READY_FOR_TAKEOFF)));
  }

  @Test (expected = CannotTakeOffException.class)
  public void testTakeOff_withPlaneThatWasNotPreparedToTakeOff_shouldThrowCannotTakeOffException() {
    plane.takeOff();
  }

  @Test
  public void testTakeOff_withPlaneThatIsPreparedToTakeOff_ShouldPutPlaneInFlight() {
    assertThat("Guard: plane should be ready for take off", plane.prepareForTakeOff(), is(equalTo(READY_FOR_TAKEOFF)));
    plane.takeOff();
    assertThat(plane.getPlaneStatus(), is(equalTo(IN_FLIGHT)));
  }

  @Test
  public void testTakeOff_withPlaneThatWarningFuelLevel_ShouldPutPlaneInFlight() {
    plane.setFuel(35);
    assertThat("Guard: plane should be ready for take off", plane.prepareForTakeOff(), is(equalTo(FUEL_WARNING)));
    plane.takeOff();
    assertThat(plane.getPlaneStatus(), is(equalTo(IN_FLIGHT)));
  }

  private PassengerPlane getWellPreparedPlaneForTakeOff() {
    when(crew.isValid()).thenReturn(true);
    return new PassengerPlane(IN_HANGER) {{ 
      setCrew(crew);
      setFuel(100);
    }};
  }
}
