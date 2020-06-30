# RGBgameoflife
https://youtu.be/8WxPSyXYiAM

This started as a random test having squares of 3 colors compete for space. As I didn't get the result I quite was looking for, I kept adding variables and parameters to try to fix that. I thought it would be cool to have some sort of control panel to adjust these variables instead of having to retype them in the code, and that's how this was born.

There's no real thing to analyze here, but the squares to look pretty cool and I like to imagine there's some deep underlying process going on that explains the patterns. But it really just is emergent behavior of largely random processes.

How the cells interact:
- Cells are identified by color; red, green, blue, or none.
- The cells can hold values of all three colors, but will only be identified by the color that has the highest value.
- A cell's color is influenced by the colors of cells around it. If a neighboring cell is red, the current cell will add some red to his    red value, and substract from the values of blue and green.
- Once a cells main value reaches 255, it starts to die.
- Even though cells grow their values when being surrounded by same colored cells, once a cell starts to die, the only thing that will      keep it alive is the presence of a different colored cell.

What the parameters do:

MIX:
Mix is a value that is multiplied to the amount of color a cell gets from a different colored cell. If a blue cell is next to a red cell and has high mix, it will recieve a lot more red than if it had also been a red cell. Likewise, a negative mix would mean the presence of red to the bule cell actually decreases the amount of red recieved by the blue cell.

DRAIN:
Drain is a value multiplied to the amount of color lost due to the neighboring of an empty cell. If a neighboring cell has no color, a high drain will subtract a lot of all colors from the current cell. A negative drain will add all colors to the cell in the presence of an empty cell.

HURT:
Hurt is how much color is lost when a cell starts to die. A high hurt amount will cause a cell to die very fast, while a low one will make this process slower.

REVIVE:
Revive affects how long it takes for a dead cell to no longer be dead (can't have cells dying and just start taking up space ya know).

SPEED:
Speed of the simulation. The lower th speed the faster.

SPAWN RATE:
How likely it is for a cell to mutate (spawn). A rate of 0 means every single cell will mutate every step, while a high value means new cells won't be randomly mutated with a color as frequently.

FULLCOLOR:
All cells contains values for all colors, but in normal rendering mode, only show up as the color they have the highest value of. Full color mode paints the cells in their actual color based on their full rgb values.

ALT:
I created two slightly different versions of the grab() function which dictated how cells pull color information from a neighboring cell and also how dying is handled by the cell. I couldn't figure out which I liked more so I put a toggle. I think I'm liking the second one more now though.
