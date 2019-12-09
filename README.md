# space_invaders
CS151: Project to recreate iconic game, Space Invaders.

Compiled in JAVA 8.

Instructions on how to play will appear in start screen. There will also be a HELP menu that
will provide further instructions on what to expect in the game.

Username: eerielocus (Michael Kang)

Username: gelier (Guiller Dalit)

Space Invaders game implementation using MVC, Command, Singleton, and Template design patterns
for CS151 in SJSU. Utilizes Blocking Queue of class Message to communicate between Model and
View while controlling multi-thread activities. It uses the Valve system for processing 
commands in Message object form to fulfill the COMMAND pattern.
 
Implements Singleton class for random number generation for alien bomb attacks:
SINGLETON design pattern involves a single class which is responsible in creating only one 
object of itself. The class will have a private constructor and will only contain public 
methods that access the object without need of instantiating the object.
 
Implements Template class for starting the game model:
TEMPLATE design pattern an abstract class exposes defined ways/templates to execute its 
methods. Subclasses can override the method implementation as per need but the invocation 
is to be in the same way as defined by the abstract class. Behavior pattern.
