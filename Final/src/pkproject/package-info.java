/**
 * This is the final project for class PK
 * I decided to write a Snake game, it follows all the requirements, 
 * I set a bonus apple for much more scores 
 * and to have the increased difficulty,
 * not only that the snake will become longer,
 * but also the refreshing will become faster.
 * 
 * the game starts from SnakeYard
 * 
 * @author Guyiyu
 *
 */
package pkproject;
/*
 ~ 用Highscorelist 存储10个Highscore
 / 做一个SnakeYard 在 Panel 里顺序点亮每一个小格实现蛇的移动
 / 信息窗口：现在的分数，现在出现的果子（颜色）和分数
 / 分数=SUM OF BONUS + LENGTH*100
 / 可选1：部分果子有时间限制。
 / 果子：随机位置；判断格子颜色（黑，重新随机*总共就250格，就重新随机即可，如果运行出bug 就实现可选2。）
 / 可选2：如果格子颜色黑，果子x++；如果超出，y++,x=0 
 / 输入名字, inputdialog
 ! 做一条新蛇去追他!
 ! 存储下Highscorelist..
 		Class 做好了, 但是不知道怎么保存/读取.
 		暂时使用的方法是存储一个 "当次的最高分". 


/*REQUIREMENT:
In order to pass the Programming Course I, you have to carry out a project
independently. The project must be implemented as a computer game. Thefollowing requirements have to be met:

 /. You must work on the project alone.
 2. The finished project must be uploaded to the corresponding subdirectory
    of your group directory (cf. Assignment 2 Task 0).
 /. The project must be edited with Eclipse.
 4. The final deadline is on 02/15/2017.
 5. 80% of the features* must be implemented.
 /. You are NOT allowed to integrate any external libraries.
 /. It is not allowed to copy larger pieces of code. If you copy smaller code
    pieces, you have to reference them.
 8. If plagiarism is detected, the examination performance will not be recognized
    and graded with "failed". We reserve the right to take further legal action.

*Features
	 1. The gameplay must be displayed visually.
	    a. The window should consist of at least three components:
	         i. Title
=	        ii. Information
	            1. Help menu
	               a. Explain control options
	               b. Explain game
	            2. Information about gameplay.
	       iii. Gameplay
	 2. The information component must be regularly updated in accordance to the gameplay.
	    a. Achieved score
	    b. Covered track
	    c. Etc.
	 3. React correctly to user input. (control character)
	    a. Keyboard or mouse controls
	 4. Place obstacles/enemies randomly
	 5. Correct collision detection
	    a. Player vs. obstacle
	    b. Player vs. screen edge
	    c. Obstacle vs. screen edge
	 6. Read and write files. (manage highscore)
	    a. Enter name.
=:	    b. Display sorted score list.
	    c. Only display the Top X.
	    d. Save/load highscore.
/	 7. The difficulty must increase during the game
	    a. After a certain period of time / score limit / track
	 8. Possibility to document the progress in the game
	    a. Obtain points
	    b. Number of lives
	    c. Duration of the game
	 9. Object-orientated programming
	    a. Instantiate enemies/obstacles as objects
	10. Satisfactory code documentation; naming of variables/methods/classes
	    a. Provide Javadoc

*/