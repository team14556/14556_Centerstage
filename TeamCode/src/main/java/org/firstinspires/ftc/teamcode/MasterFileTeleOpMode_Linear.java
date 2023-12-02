/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/*
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When a selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@TeleOp(name="Master File: Linear OpMode", group="Linear OpMode")
public class MasterFileTeleOpMode_Linear extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null; //left wheel motor
    private DcMotor rightDrive = null; //right wheel motor

    private DcMotor armDrive = null; //arm motor

    private Servo clawDrive = null; //claw servo

    private DcMotor liftDrive = null;

    private Servo armServoDrive = null;



    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        armDrive = hardwareMap.get(DcMotor.class, "arm_drive");
        armDrive.setDirection(DcMotor.Direction.FORWARD);
        armDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        clawDrive = hardwareMap.servo.get("claw_drive");
        clawDrive.setDirection(Servo.Direction.FORWARD);
        clawDrive.setPosition(0.25);

        liftDrive = hardwareMap.get(DcMotor.class, "lift_drive");

        liftDrive.setDirection(DcMotor.Direction.FORWARD);

        liftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        armServoDrive = hardwareMap.servo.get("arm_servo_drive");


        armServoDrive.setPosition(0.8);

        double maxservo=0.8;
        double minservo=0.39375;
        long startTime = System.currentTimeMillis();

        waitForStart();
        runtime.reset();
        liftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {


            if (gamepad1.right_bumper) {
                leftDrive.setPower(-gamepad1.left_stick_y*0.5);
                rightDrive.setPower(-gamepad1.right_stick_y*0.5);
            }
            else {
                leftDrive.setPower(-gamepad1.left_stick_y);//power that goes to left wheel motor
                rightDrive.setPower(-gamepad1.right_stick_y); //power that goes to right wheel motor

            }



            if (gamepad2.right_stick_y < 0) {//down

                armDrive.setDirection(DcMotor.Direction.FORWARD);

                int xTarget=0;


                while(opModeIsActive() && armDrive.getCurrentPosition() < xTarget && gamepad2.right_stick_y < 0) {
                    armDrive.setPower(0.4);

                }
                telemetry.addData("Status", armDrive.getCurrentPosition());
                telemetry.update();

                armDrive.setPower(0);


            }

            else if (gamepad2.right_stick_y > 0) {//up


                armDrive.setDirection(DcMotor.Direction.FORWARD);
                int yTarget=-2130;

                while(opModeIsActive() && armDrive.getCurrentPosition() > yTarget && gamepad2.right_stick_y > 0) {
                    armDrive.setPower(-0.4);

                }


                armDrive.setPower(0);
            }


            //claw:
            if (gamepad2.cross) {
                clawDrive.setPosition(0.25);

            }
            if (gamepad2.triangle) {
                clawDrive.setPosition(0.15);

            }
            liftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


            while (gamepad2.right_bumper) {

                if (gamepad2.left_stick_y < 0) {

                    liftDrive.setDirection(DcMotor.Direction.FORWARD);

                    int xTarget = 4915;


                    //armDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    //armDrive.setTargetPosition(newTarget);
                    //armDrive.setPower(0.5);
                    while (opModeIsActive() && liftDrive.getCurrentPosition() < xTarget && gamepad2.left_stick_y < 0) {
                        liftDrive.setPower(0.5);

                    }

                    liftDrive.setPower(0);


                } else if (gamepad2.left_stick_y > 0) {


                    liftDrive.setDirection(DcMotor.Direction.FORWARD);
                    int yTarget = -5000;

                    while (opModeIsActive() && liftDrive.getCurrentPosition() > yTarget && gamepad2.left_stick_y > 0) {
                        liftDrive.setPower(-0.5);

                    }


                    liftDrive.setPower(0);


                }

            }
            if (gamepad2.left_stick_y > 0) {

                liftDrive.setDirection(DcMotor.Direction.FORWARD);

                int xTarget = 4915;


                //armDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                //armDrive.setTargetPosition(newTarget);
                //armDrive.setPower(0.5);
                while (opModeIsActive() && liftDrive.getCurrentPosition() < xTarget && gamepad2.left_stick_y > 0) {
                    liftDrive.setPower(0.5);

                }

                liftDrive.setPower(0);


            } else if (gamepad2.left_stick_y < 0) {


                liftDrive.setDirection(DcMotor.Direction.FORWARD);
                int yTarget = 0;

                while (opModeIsActive() && liftDrive.getCurrentPosition() > yTarget && gamepad2.left_stick_y < 0) {
                    liftDrive.setPower(-0.5);

                }


                liftDrive.setPower(0);


            }
            while (gamepad2.right_trigger>0) {//0.8

                if (armServoDrive.getPosition()<maxservo && System.currentTimeMillis()>startTime+100) {
                    startTime=System.currentTimeMillis();

                    armServoDrive.setPosition(armServoDrive.getPosition()+0.0325);
                }

                //armServoDrive.setPosition(0.8);


            }


            while (gamepad2.left_trigger>0) {//0.39375

                    if (armServoDrive.getPosition()>minservo && System.currentTimeMillis()>startTime+100) {

                        startTime=System.currentTimeMillis();
                        armServoDrive.setPosition(armServoDrive.getPosition()-0.0325);
                    }



                }
        }

    }
}
