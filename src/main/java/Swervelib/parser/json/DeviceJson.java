package Swervelib.parser.json;

import com.revrobotics.SparkMaxRelativeEncoder.Type;

import Swervelib.encoders.AnalogAbsoluteEncoderSwerve;
import Swervelib.encoders.CANCoderSwerve;
import Swervelib.encoders.SparkMaxEncoderSwerve;
import Swervelib.encoders.SwerveAbsoluteEncoder;
import Swervelib.imu.PigeonSwerve;
import Swervelib.imu.SwerveIMU;
import Swervelib.motors.SparkMaxBrushedMotors;
import Swervelib.motors.SparkMaxSwerve;
import Swervelib.motors.SwerveMotors;
import Swervelib.motors.SwerveTalonFX;
import Swervelib.motors.SwerveTalonSRX;

/**
 * Device JSON parsed class. Used to access the JSON data.
 */
public class DeviceJson
{

  /**
   * The device type, e.g. pigeon/pigeon2/sparkmax/talonfx/navx
   */
  public String type;
  /**
   * The CAN ID or pin ID of the device.
   */
  public int    id;
  /**
   * The CAN bus name which the device resides on if using CAN.
   */
  public String canbus = "";

  /**
   * Create a {@link SwerveAbsoluteEncoder} from the current configuration.
   *
   * @return {@link SwerveAbsoluteEncoder} given.
   */
  public SwerveAbsoluteEncoder createEncoder()
  {
    switch (type)
    {
      case "none":
      case "integrated":
      case "attached":
        return null;
      case "thrifty":
      case "throughbore":
      case "dutycycle":
      case "analog":
        return new AnalogAbsoluteEncoderSwerve(id);
      case "cancoder":
        return new CANCoderSwerve(id, canbus != null ? canbus : "");
      default:
        throw new RuntimeException(type + " is not a recognized absolute encoder type.");
    }
  }

  /**
   * Create a {@link SwerveIMU} from the given configuration.
   *
   * @return {@link SwerveIMU} given.
   */
  public Swervelib.imu.SwerveIMU createIMU()
  {
    switch (type) {
      //case "adis16448":
        //return new ADIS16448Swerve();
      //case "adis16470":
        //return new ADIS16470Swerve();
      //case "adxrs450":
        //return new ADXRS450Swerve();
      //case "analog":
        //return new AnalogGyroSwerve(id);
      //case "navx_onborard":
        //return new NavXSwerve(Port.kOnboard);
      //case "navx_usb":
        //return new NavXSwerve(Port.kUSB);
      //case "navx_mxp":
      //case "navx":
        //return new NavXSwerve(Port.kMXP);
      case "pigeon":
        return new PigeonSwerve(id);
      //case "pigeon2":
       // return new Pigeon2Swerve(id, canbus != null ? canbus : "");
      default:
        throw new RuntimeException(type + " is not a recognized absolute encoder type.");
    }
  }

  /**
   * Create a {@link SwerveMotor} from the given configuration.
   *
   * @param isDriveMotor If the motor being generated is a drive motor.
   * @return {@link SwerveMotor} given.
   */
  public SwerveMotors createMotor(boolean isDriveMotor)
  {
    switch (type)
    {
      case "sparkmax_brushed":
        switch (canbus)
        {
          case "greyhill_63r256":
            return new SparkMaxBrushedMotors(id, isDriveMotor, Type.kQuadrature, 1024, false);
          case "srx_mag_encoder":
            return new SparkMaxBrushedMotors(id, isDriveMotor, Type.kQuadrature, 4096, false);
          case "throughbore":
            return new SparkMaxBrushedMotors(id, isDriveMotor, Type.kQuadrature, 8192, false);
          case "throughbore_dataport":
            return new SparkMaxBrushedMotors(id, isDriveMotor, Type.kNoSensor, 8192, true);
          case "greyhill_63r256_dataport":
            return new SparkMaxBrushedMotors(id, isDriveMotor, Type.kQuadrature, 1024, true);
          case "srx_mag_encoder_dataport":
            return new SparkMaxBrushedMotors(id, isDriveMotor, Type.kQuadrature, 4096, true);
          default:
            if (isDriveMotor)
            {
              throw new RuntimeException("Spark MAX " + id + " MUST have a encoder attached to the motor controller.");
            }
            // We are creating a motor for an angle motor which will use the absolute encoder attached to the data port.
            return new SparkMaxBrushedMotors(id, isDriveMotor, Type.kNoSensor, 0, false);
        }
      case "neo":
      case "sparkmax":
        return new SparkMaxSwerve(id, isDriveMotor);
      case "falcon":
      case "talonfx":
        return new SwerveTalonFX(id, canbus != null ? canbus : "", isDriveMotor);
      case "talonsrx":
        return new SwerveTalonSRX(id, isDriveMotor);
      default:
        throw new RuntimeException(type + " is not a recognized absolute encoder type.");
    }
  }

  /**
   * Create a {@link SwerveAbsoluteEncoder} from the data port on the motor controller.
   *
   * @param motor The motor to create the absolute encoder from.
   * @return {@link SwerveAbsoluteEncoder} from the motor controller.
   */
  public SwerveAbsoluteEncoder createIntegratedEncoder(SwerveMotors motor)
  {
    switch (type)
    {
      case "sparkmax":
        return new SparkMaxEncoderSwerve(motor);
      case "falcon":
      case "talonfx":
        return null;
    }
    throw new RuntimeException(
        "Could not create absolute encoder from data port of " + type + " id " + id);
  }
}
