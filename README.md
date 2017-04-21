[![](https://jitpack.io/v/Fupery/InvMenu.svg)](https://jitpack.io/#Fupery/InvMenu)

# InvMenu
### A simple inventory GUI library for Spigot plugins.

## Setup
#### Using Maven
1. Add the following tags to your pom.xml:
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependencies>  
  <dependency>
    <groupId>com.github.Fupery</groupId>
    <artifactId>InvMenu</artifactId>
    <version>1.1</version>
  </dependency>
</dependencies>
```
2. Use the maven shade plugin to package the libary into your project.
Example setup:
```xml
<plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-shade-plugin</artifactId>
        <version>2.3</version>
        <executions>
            <execution>
                <phase>package</phase>
                <goals>
                    <goal>shade</goal>
                </goals>
                <configuration>
                    <minimizeJar>false</minimizeJar>
                    <artifactSet>
                        <includes>
                            <include>com.github.Fupery:InvMenu:*</include>
                        </includes>
                    </artifactSet>
                </configuration>
            </execution>
        </executions>
    </plugin>
</plugins>
```
You can find more informaton about shading dependencies [here](https://maven.apache.org/plugins/maven-shade-plugin/).

#### Using Other Dependency Management
Check out the examples [here](https://jitpack.io/#Fupery/InvMenu/1.2) for other dependency management tools.


## Using InvMenu
#### Getting Started
To get started creating custom menus, you'll need a **MenuHandler**. Every plugin has one MenuHandler, it is accessed using the Menu class:
```Java
MenuHandler handler = Menu.getMenuHandler(yourPlugin);
```
You can use the handler to open, close, or refresh your menus.
```Java
handler.openMenu(player, someCoolMenu);
handler.refreshMenu(player);
handler.closeMenu(player, MenuCloseReason.DONE);
```

#### Creating Menus
A menu has a title, an InventoryType that determines its appearance, and a collection of buttons. 
When a player views this menu, it is represented on the player's screen by an inventory button -
the buttons are represented by items that sit in the inventory's slots. 


Each menu represents a single screen - to nest one menu in another, you can use buttons (more on that later). 
Multiple players can view the same menu, but each player can only view one menu at a time.


When the player clicks one of these items, the button associated with this item is told which player clicked it, 
and what type of click the player used (left-click, right-click, etc.).

You can create your own menu types that extend the basic menu classes, but the easiest way to get started is with a **BasicMenu**.
```Java
MenuHandler handler = Menu.getMenuHandler(yourPlugin);
BasicMenu myMenu = new BasicMenu(null, "My Menu.", InventoryType.CHEST);
```

Add buttons to the menu by overriding the class's getButtons#Button[] method, and returning an array of buttons.
There are lots of different types of buttons, and you can create your own, too! Some examples are shown in the menu below.
```Java
MenuHandler handler = Menu.getMenuHandler(yourPlugin);
//Create a new basicMenu
BasicMenu myMenu = new BasicMenu(null, "My cool menu!", InventoryType.CHEST) {
    @Override
    public Button[] getButtons() {
        return new Button[]{
                new StaticButton(Material.FIRE, "Nothing happens if you click me!"),
                new StaticButton(Material.BOOK, "Same here!", new String[]{"Whoah", "more", "lines", "of", "text!"}),
                new LinkedButton(someOtherMenu, Material.CHEST, "If you click me, another menu will open!"),
                new CloseButton(this), //If you click me, the menu will close!
                new Button(Material.FEATHER) { //I'm a cool custom button!
                    @Override
                    public void onClick(Player player, ClickType clickType) {
                        if (clickType == ClickType.LEFT) player.setWalkSpeed(10000);
                        else if (clickType == ClickType.RIGHT) player.setWalkSpeed(1);
                    }
                }
        };
    }
};
 
````
For more examples of menus, check out [ArtMap's menu structure](https://github.com/Fupery/ArtMap/tree/master/src/main/java/me/Fupery/ArtMap/Menu/HelpMenu).

#### Menu Factories
You could construct a new Menu instance every time you want to open a menu, but if you're 
showing multiple players the same one over and over, there's no point re-creating the menu every time. 
You can use menu factories to instantiate and store menus for when you need them.

__You don't need to use these, but they can be helpful and simplify your code__

Factories have a get#Player method that will return the correct menu for the player provided.

There are 3 basic types of MenuFactories included in InvMenu, with different strategies for creating menus:
##### Static Menu Factory
This factory weakly holds a reference to a single menu, and will return the same menu for every player.
Use this for when you want every player to see the same menu, like in a help screen.
##### Dynamic Menu Factory
This factory instantiates a new menu for each player, meaning each player's menu will be different.
Use this when you want players to have a custom menu, like an option screen.
##### Conditional Menu Factory
This factory has an associated boolean condition, and stores two menus: one for if the condition is true, and one for if
it is false. For example, if the condition is:
```Java
player.hasPermission("yourPlugin.admin");
```
You could use a Conditional menu factory to return an admin menu if the player has this permission node, and return a player menu if they don't.

---

Feel free to PR or suggest any features.
