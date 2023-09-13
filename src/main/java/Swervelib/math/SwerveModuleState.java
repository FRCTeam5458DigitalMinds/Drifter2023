package Swervelib.math;

import edu.wpi.first.math.geometry.Rotation2d;

/**
 * Second order kinematics swerve module state.
 */
public class SwerveModuleState extends SwerveModuleState
{

  /**
   * Swerve module speed in meters per second.
   */
  public double     speedMetersPerSecond;
  /**
   * Rad per sec
   */
  public double     omegaRadPerSecond = 0;
  /**
   * Swerve module angle as a {@link Rotation2d}.
   */
  public Rotation2d angle             = Rotation2d.fromDegrees(0);

  /**
   * Constructs a SwerveModuleState with zeros for speed and angle.
   */
  public SwerveModuleState()
  {
  }

  /**
   * Constructs a SwerveModuleState.
   *
   * @param speedMetersPerSecond The speed of the wheel of the module.
   * @param angle                The angle of the module.
   * @param omegaRadPerSecond    The angular velocity of the module.
   */
  public SwerveModuleState(
      double speedMetersPerSecond, Rotation2d angle, double omegaRadPerSecond)
  {
    this.speedMetersPerSecond = speedMetersPerSecond;
    this.angle = angle;
    this.omegaRadPerSecond = omegaRadPerSecond;
  }
}