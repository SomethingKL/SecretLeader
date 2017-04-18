# SecretLeader
data:
  Images:
    Blue.jpg- The board for the blue team.
    BlueCard.jpg- The Blue Team party card.
    BluePolicy.jpg-The Blue Team policy card.
    Chancellor.jpg- The position of Chancellor's picture.
    NoCard.PNG-The voting card for "No".
    President.jpg-The position of President's picture.
    Red.jpg- The board for the red team.
    RedCard.jpg-The Red Team party card.
    RedLeader.jpg-The Red Team leader, which is the same as the Red Team card.
    RedPolicy.jpg-The Red Team policy card.
    YesCard.PNG-The voting card for "Yes".
  Files:
    Board.txt-The text file that keeps track of the number of cards on the board for the red and blue teams.
    leaveStarting.txt- The file the tells the game to either stay or levae the initial screen.
    Players.txt-The players in order of joining the game.
    Roles.txt-The players in order of joining the game with their respective role: Secret Leader, Blue Team and Red Team.
    state.txt-The current state of the game, the enumeration from SLPanel: JOINING, PLAYING, VOTING, WAITING,POLICY, GAMEOVER
    Turn.txt- Who the president and chancellor are.
    VotingFile.txt- Yes's and no's, the votes from the players durring the game.
    VotingScreen.txt- The policies being transferred.

Classes:
  entity-Anything that has an image and is drawn is an entity.
    Board.java-It holds all the board information for one team, either blue or red.
    Official.java-Displays the chancellor or presidential role.
    PlayerCard.java-This is our secret leader player card.
    PlayerList.java-The Player's being displayed.
    PolicyCard.java-A policy card for the game.
    PolicySelection.java-A collection of the PolicyCard's, selects through all of the cards for the president and chancellor.
    VictoryCard.java-????
    Votecard.java- A yes or no card with functionality.
    Votingcard.java-Early version of votecard; Not active now.
  framework-The main aspects of how the game runs.
    Controller.java-Decides what to display on the board. Runs most of the logic for the game.
    DocumentSizeFilter.java-For main menu, makes the textpane have a limitation of characters.
    Driver.java-The beginning of the game.
    MainMenu.java-The set up for the game; resets files, gets username and other things to begin the game.
    TCPClient.java-File in and out.
  ui-Mainly what the user sees.
    SLCanvas.java-Abstract drawing canvas.
    SLFrame.java-The only frame in the game; where the program is ran on.
    SLPanel.java-Decides what state the game is in, dealing with enumerations. Extends SLCanvas
      to do most of the drawing in the game. 
  
