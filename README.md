# SecretLeader
## Data  
  ### Images:  
    Blue.jpg          - The board for the blue team.  
    BlueCard.jpg      - The Blue Team party card.  
    BluePolicy.jpg    - The Blue Team policy card.  
    Chancellor.jpg    - The position of Chancellor's picture.  
    NoCard.PNG        - The voting card for "No".  
    President.jpg     - The position of President's picture.  
    Red.jpg           - The board for the red team.  
    RedCard.jpg       - The Red Team party card.  
    RedLeader.jpg     - The Red Team leader, which is the same as the Red Team card.  
    RedPolicy.jpg     - The Red Team policy card.  
    YesCard.PNG       - The voting card for "Yes".  
 ### Files:  
    Board.txt         - The file that maintains of the number of cards on the board for the red and blue teams.  
    leaveStarting.txt - The file the tells the game to either stay or levae the initial screen.  
    Players.txt       - The players in joining order.  
    Roles.txt         - The players in joinging order with their roles: Secret Leader, Blue Team and Red Team.  
    state.txt         - The current game state, SLPanel enum: JOINING, PLAYING, VOTING, WAITING,POLICY, GAMEOVER  
    Turn.txt          - Who the president and chancellor are.  
    VotingFile.txt    - Yes's and no's, the votes from the players durring the game.  
    VotingScreen.txt  - The policies being transferred.  

## Classes  
 ### Entity - Anything that has an image and is drawn is an entity
    Board.java              - It holds all the board information for one team, either blue or red.  
    Official.java           - Displays the chancellor or presidential role.  
    PlayerCard.java         - This is our secret leader player card.  
    PlayerList.java         - The Player's being displayed.  
    PolicyCard.java         - A policy card for the game.  
    PolicySelection.java    - A collection of PolicyCards, selects cards for the president and chancellor.  
    VictoryCard.java        - ????  
    Votecard.java           - A yes or no card with functionality.  
    Votingcard.java         - Early version of votecard; Not active now.  
 ### Framework - The main aspects of how the game runs 
    Controller.java         - Decides what to display on the board. Runs most of the logic for the game.  
    DocumentSizeFilter.java - For main menu, makes the textpane have a limitation of characters.  
    Driver.java             - The beginning of the game.  
    MainMenu.java           - Game setup: resets files, gets username and other tasks to begin the game.  
    TCPClient.java          - File in and out.  
 ### UI - user visuals  
    SLCanvas.java           - Abstract drawing canvas.  
    SLFrame.java            - The only frame in the game; where the program is ran on.  
    SLPanel.java            - Decides game state based on enumberation. Extends SLCanvas for standard drawing.   
  
