# RaspiCar

Commands:
- 1:angle; set steering servo angle; response: 1 if sucessful
- 2:speed; set right motor speed; response: 1 if sucessful
- 3:speed; set left motor speed; response: 1 if sucessful
- 4:angle; set camera tilt angle; response: 1 if sucessful
- 5:angle; set camera pan angle; response: 1 if sucessful
- 6:port; start video stream on specified port; response: link to stream
- 7:0; stop video stream; response: 1 if sucessful
- 8:0; request ultrasonic sensor reading; response: distance
- 9:0; request grayscale sensor reading; response: grayscale_status:line_status:cliff_status
- 10:text:volume:lang; speak text; response: 1 if sucessful
- 11:file:volume; play audio file; response: 1 if sucessful

