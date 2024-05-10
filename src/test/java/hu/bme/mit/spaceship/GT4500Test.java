package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private TorpedoStore mockPrimary;
  private TorpedoStore mockSecondary;
  private GT4500 ship;

  @BeforeEach
  public void init() {
    mockPrimary = mock(TorpedoStore.class);
    mockSecondary = mock(TorpedoStore.class);
    this.ship = new GT4500(mockPrimary, mockSecondary);
  }

  @Test
  public void fireTorpedo_Single_Success() {
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimary, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Success() {
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Alternating_Success() {
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(1)).fire(1);
    assertEquals(true, result1);
    assertEquals(true, result2);
  }

  @Test
  public void fireTorpedo_Alternating_SecondaryEmpty_Success() {
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimary, times(2)).fire(1);
    verify(mockSecondary, times(1)).isEmpty();
    assertEquals(true, result1);
    assertEquals(true, result2);
  }

  @Test
  public void fireTorpedo_Alternating_FirstEmpty_Success() {
    // Arrange
    when(mockSecondary.fire(1)).thenReturn(true);
    when(mockPrimary.isEmpty()).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockSecondary, times(1)).fire(1);
    verify(mockPrimary, times(1)).isEmpty();
    assertEquals(true, result1);
  }

  @Test
  public void fireTorpedo_All_FirstEmpty_Success() {
    // Arrange
    when(mockPrimary.isEmpty()).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimary, times(1)).isEmpty();
    assertEquals(false, result1);
  }

  @Test
  public void fireTorpedo_All_SecondEmpty_Success() {
    // Arrange
    when(mockSecondary.isEmpty()).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimary, times(1)).isEmpty();
    assertEquals(false, result1);
  }

  @Test
  public void fireLaser_UnSuccessFul() {
    boolean result = ship.fireLaser(FiringMode.SINGLE);

    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_All_FirstFireFalse_Success() {
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(false);
    when(mockSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_SecondFireFalse_Success() {
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_AllFireFalse_Success() {
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(false);
    when(mockSecondary.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(1)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_Single_FirstTwice_UnSuccessful() {
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    when(mockPrimary.isEmpty()).thenReturn(true);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(1)).isEmpty();
    assertEquals(true, result1);
    assertEquals(false, result2);
  }

  @Test
  public void fireTorpedo_Single_FirstEmpty_SecondaryEmpty_Successful(){
    when(mockPrimary.isEmpty()).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockPrimary, times(1)).isEmpty();
    verify(mockSecondary, times(1)).isEmpty();
    assertEquals(false, result);
  }

  public void fireTorpedoDefault(){
    boolean result = ship.fireTorpedo(FiringMode.DEF);

    assertEquals(false, result);
  }
}
