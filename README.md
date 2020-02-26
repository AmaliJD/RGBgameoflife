# RGBgameoflife
Like Conway's game of life but not really at all.

This started as a random test having squares of 3 colors compete for space. As I didn't get the result I quite was looking for, I kept adding variables and parameters to try to fix that. I thought it would be cool to have some sort of control panel to adjust these variables instead of having to retype them in the file, and that's how this was born.

There's no real thing to analyze here, but the squares to look pretty cool and I like to imagine there's some deep underlying process going on that explains the patterns. But it really just is emergent behavior of largely random processes.

How the cells interact:
- Cells are identified by color; red, green, blue, or none
- The cells can hold values of all three colors, but will only be identified by the color that has the highest value
- A cell's color is influenced by the colors of cells around it. If a neighboring cell is red, the current cell will add some red to his red value, and substract from the values of blue and green.

What the parameters do:
MIX:
DRAIN:
HURT:
REVIVE:
SPEED:
SPAWN RATE:
FULLCOLOR:
ALT:
