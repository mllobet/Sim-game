Project 1
=========

Implementation of The game of Sim and player AI for CS61BL @UC Berkeley

  - [Project Description]
  - [Game Description]
 
Overview
-
The Game of Sim is a simple [zero sum] two-player game played in a board with six points representing a regular Hexagon, each player takes turns to draw a line connecting two points , each using his own color.  

The first player to be forced to form a triangle of his own color (only triangles whose vertexes are among .the six starting points count) is the loser. 


Strategies
-
There actually is a [winning strategy] for the second player ([implementation]), but it is COMPLICATED, the next best strategies are search algorithms based on prunning the search tree of the [minimax algorithm]:

  - [Strategic game programming] Sublime explanation, most of the algorithms are there
  - [Game playing lectures]
  - [α ß prunning]
  - [Negascout] (Outperforms minimax)



  [Project Description]: http://www-inst.eecs.berkeley.edu/~cs61bl/su13/projects/proj1.pdf    
  [Game Description]: https://dl.dropboxusercontent.com/u/6691894/The%20game%20of%20Sim.pdf
  [zero sum]: https://en.wikipedia.org/wiki/zero_sum
  [winning strategy]: http://www.jstor.org/discover/10.2307/2688046?uid=3739560&uid=2129&uid=2&uid=70&uid=4&uid=3739256&sid=21102437364301
  [implementation]: http://puzzles.net23.net/sim.htm
  [minimax algorithm]: https://en.wikipedia.org/wiki/Minimax#Minimax_algorithm_with_alternate_moves
  [Strategic game programming]: http://fierz.ch/strategy.htm
  [Game playing lectures]: http://webdocs.cs.ualberta.ca/~jonathan/PREVIOUS/Courses/657/Notes/
  [α ß prunning]: https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning
  [Negascout]: https://en.wikipedia.org/wiki/Negascout
