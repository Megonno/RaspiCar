# RaspiCar

## Commands:


| Command        | Description                                                            | Response                                      |Response Prefix|
|----------------|------------------------------------------------------------------------|-----------------------------------------------|---------------|
| 1:angle        | set steering servo angle                                               | 1 if successful                               ||
| 2:speed        | set forward speed                                                      | 1 if successful                               ||
| 3:speed        | set backwards speed                                                    | 1 if successful                               ||
| 4:angle        | set camera tilt angle                                                  | 1 if successful                               ||
| 5:angle        | set camera pan angle                                                   | 1 if successful                               ||
| 6:0            | start video stream                                                     | None                                          ||
| 7:0            | stop server                                                            |                                               ||
| 8:0            | request ultrasonic sensor reading                                      | distance                                      |1:|
| 9:0            | request grayscale sensor reading                                       | raw_grayscale_sensor_readings                 |2:|
| 10:text:lang   | speak text with custom language                                        | 1 if successful                               ||
| 11:file:volume | play audio file (file must be in the same folder as the server script) | 1 if successful                               ||

## Usage:

1. start the server script on the car.
2. start the client script on the computer that will control the car
3. using the client, send the specified commands
