#Conway's Game Of Life
An application that, given an initial state of the universe, will calculate the next generation following the rules of the game.

See [https://en.wikipedia.org/wiki/Conway's_Game_of_Life](#https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life)

##Usage
1) Test the application using gradle:

    
    $ ./gradlew test

2) Build the application using gradle:


    $ ./gradlew jar
    
3) Execute the application:

    
    $ java -jar build/libs/game-of-life-0.0.1.jar
    usage: <height> <width> <file> [delimiter]
        height    - the number of rows in your universe
        width     - the number of columns in your universe
        file      - a file containing the initial state of your universe
        delimiter - an optional pattern which separates cells in the universe (default: ',')
        
The application expects three required arguments and a fourth optional argument.

The file should consist of *height* lines, each of which contain *width* 0's or 1's separated by a single comma (or a custom delimiter). A *0* is interpreted as a dead cell; and *1* as a live cell.

Ex: testUniverse

    0,0,0,0,0,0,1,0
    1,1,1,0,0,0,1,0
    0,0,0,0,0,0,1,0
    0,0,0,0,0,0,0,0
    0,0,0,1,1,0,0,0
    0,0,0,1,1,0,0,0
    
A few initial states, each of size 6x8 (HxW), exist in the resources directory:

- testUniverse
- packedUniverse
- funUniverse


    $ java -jar build/libs/game-of-life-0.0.1.jar 6 8 testUniverse
    
    Universe initialized:
    0 0 0 0 0 0 1 0 
    1 1 1 0 0 0 1 0 
    0 0 0 0 0 0 1 0 
    0 0 0 0 0 0 0 0 
    0 0 0 1 1 0 0 0 
    0 0 0 1 1 0 0 0 
    
    Advancing to next state...done
    0 1 0 0 0 0 0 0 
    0 1 0 0 0 1 1 1 
    0 1 0 0 0 0 0 0 
    0 0 0 0 0 0 0 0 
    0 0 0 1 1 0 0 0 
    0 0 0 1 1 0 0 0 
    
    Continue advancing the state?[Y/n] n

