package test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import main.AirTrafficControl;
import main.Plane;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AirTrafficControlTest {

  @Mock private Plane plane;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testPlainTakeOff() {
    AirTrafficControl airTrafficControl = new AirTrafficControl();
    airTrafficControl.takeOff(plane);
    verify(plane, times(1)).takeOff();
  }
}
