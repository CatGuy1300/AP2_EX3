# Advanced Programing 2 Exercise 3
In this exercise we coded a flight simulator control app.

## Usage And Features

#### Special Features ####
1. IP and Port are saved after clicking on "connect"
2. You can click "connect" again to try and reconnect if need to

## Files And Directories
#### Directories
There are some main Directories in the project:  
1. model: There are the model's files
2. view_model: there are the view_model's files
3. views: there are the view's files
4. res: there are resources, like layouts.
#### Files
The main files are the src code files, like the FGModel.java,  ViewModel.java,  Joystick.java, MainActivity.java, and the xml for the MainActivity layout, activity_main.xml.  
There are also files that are part of every android project.

## Development
This app was developed on android studio using java


## Installation and Running
#### Installation
Clone this git repository into a directory in your computer.  
**About FG:** If you want to use FG, you should install it, and put the "playback_small.xml" from the moodle in the folder "/data/protocols", inside the folder you installed FG in.
#### Running
If you want to run FG manually, you should to it with the next settings:
```
--generic=socket,in,10,127.0.0.1,6400,tcp,playback_small
```
(You can use different values for 127.0.0.1, 6400 for IP and port, but you should use these)  
Use an IDE which supports running android projects to open the project from the directory you cloned it into earlier. Now from your IDE, run the app.  

## Design
You can see here our [UML diagram](https://online.visual-paradigm.com/community/share/ap2-ex3-vpd-lkr0hgjxp) or [here](https://online.visual-paradigm.com/app/diagrams/#G18e4zO-XyvWYFPzMT15UmFuwRWdz7XwK_)
#### Model, VMs and Views (MVVM)
The main architecture we use in the project is the MVVM architecture.  
There is data binding between the view (MainActivity) and the ViewModel, the View command the ViewModel to change its, and it commands the Model, which sends a message to the FG. The Model notifies the VM on events, which notifies the View, events like that the Model connected to FG.



## Video
[video](https://youtu.be/7DPictQIYhM)
