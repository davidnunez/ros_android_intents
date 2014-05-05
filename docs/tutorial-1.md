# rosjava tutorial

## Goals

- You have a development environment on which you can deploy ROS compatible apps to android devices.

- You can compile and deploy a Broadcast Intent demo app to a simulated and physical android device.

- You can compile and deploy a ROS compatible Android app that interprets and relays arbitrary android broadcast intents to roscore on a simulator and physical android device.

## TODO topics 
- how to run roscore on device
- custom messages
- Unity broadcast intent
- [https://github.com/intrications/intent-intercept][1]

## Prerequisites
- Ubuntu set up to support PRG development via [machine\_config][2]
- Android Device running minimum API 16 (`JELLY_BEAN`)

## Setup Android Development Environment
1. add ROS to your environment

		echo "source /opt/ros/hydro/setup.bash" >> ~/.bashrc
		source ~/.bashrc

1. Install ROS android packages via [installation tutorial][3]
	- Step by step instructions (as of 2014-05-05)
		- Notes:
			- You probably don’t need `ia32-libs`
			- (be sure to follow _all_ of the instructions under Android Studio Download (ex. installing extra Android builds in the SDK Manager) 
			- Follow instructions "Fat Workspace" option under _Sources_ since we do so much w/ Android devices.
				- _2014-04-12_ : don't opt for apps/android remocons (related to Robotics in Concert project) repos -- there seems to be an issue with the repositories and gradle versions that hasn't been resolved in these repos.

		1. Get JDK:

				$ sudo apt-get update
				$ sudo apt-get install openjdk-7-jdk
				$ sudo add-apt-repository ppa:webupd8team/java -y
				$ sudo apt-get update
				$ sudo apt-get install oracle-java7-installer
				# Switch back to openjdk anytime with sudo update-alternatives --config java

2. Download [Android Studio Bundle][4] (_as of 2014-05-05, version 135-1078000_) to `~/Downloads`
	3. untar the download and add it to your path:

			$ cd ~/Downloads
			$ sudo tar -zxvf android-studio-bundle-135.1078000-linux.tgz -C /opt/
			$ sudo chown -R prg:prg /opt/android-studio/
			$ echo export PATH=\${PATH}:/opt/android-studio/sdk/tools:/opt/android-studio/sdk/platform-tools:/opt/android-studio/bin >> ~/.bashrc
			$ echo export ANDROID_HOME=/opt/android-studio/sdk >> ~/.bashrc
			$ source ~/.bashrc
		- Open the Android SDK manager and install (_this step takes a while_):
			- SDK Build Tools 19.0.2
			- SDK Build Tools 19.0.3
			- Android API 10
			- Android API 13
			- Android API 15
			- Android API 16
			- Android API 18

					$ android
		- Launch Android Studio and update the first time

				$ studio.sh
			- click on “Check for update” link at the bottom of the window and apply any upgrades
			- Exit studio
		- Install Ros Java (_Note: the catkin\_make step takes a while):

				$ sudo apt-get install ros-hydro-catkin ros-hydro-ros ros-hydro-rosjava python-wstool
				$ mkdir -p ~/android
				$ wstool init -j4 ~/android/src https://raw.github.com/rosjava/rosjava/hydro/android_core.rosinstall
				$ source /opt/ros/hydro/setup.bash
				$ cd ~/android
				$ catkin_make
3. Get ros\_android\_intents project

		$ cd ~/android/src/
		$ git clone https://github.com/davidnunez/ros_android_intents
 4. launch Android Studio

		$ studio.sh
4. **Import** `~/android/src/ros_android_intents` as a new project
	- When selecting the location of the project, choose the root folder `~/android/src/rs_android_intents`
	- It will take a while to import and auto build -- stuff will be scrolling in the terminal window as it's working

3. (Optional) Import `~/android/src/android_core` as a new project
	- It will take a while to import and auto build -- stuff will be scrolling in the terminal window as it's working
	** As of 2014-03-17, there was still an issue requiring updating gradle plugin. There is a [fix][5].**

## Run Broadcast Demo

## Updates / troubleshooting:


- If you are reinstalling studio, you may need to Execute these before build (_per [https://code.google.com/p/android/issues/detail?id=61573#c19][6]_)

		find /.gradle/caches/ -iname ".jar" -exec zip -d '{}' 'META-INF/NOTICE' \;
		find /.gradle/caches/ -iname ".jar" -exec zip -d '{}' 'META-INF/LICENSE' \;


Additional References and Notes:

- [A good description of Android Broadcast Intents and Receivers][7]

- https://github.com/intrications/intent-intercept
- https://github.com/personal-robots/machine_config "machine_config"
- http://wiki.ros.org/android/Tutorials/hydro/Installation%20-%20Ros%20Development%20Environment
- http://developer.android.com/sdk/installing/studio.html#download
- https://github.com/rosjava/android_core/issues/198
- https://code.google.com/p/android/issues/detail?id=61573#c19
- http://www.techotopia.com/index.php/Android_Broadcast_Intents_and_Broadcast_Receivers

[1]:	https://github.com/intrications/intent-intercept
[2]:	https://github.com/personal-robots/machine_config "machine_config"
[3]:	http://wiki.ros.org/android/Tutorials/hydro/Installation%20-%20Ros%20Development%20Environment
[4]:	http://developer.android.com/sdk/installing/studio.html#download
[5]:	https://github.com/rosjava/android_core/issues/198
[6]:	https://code.google.com/p/android/issues/detail?id=61573#c19
[7]:	http://www.techotopia.com/index.php/Android_Broadcast_Intents_and_Broadcast_Receivers
