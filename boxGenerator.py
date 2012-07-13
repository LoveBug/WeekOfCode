import random

width=300
height=24

print str(width) + " " + str(height)
for  i in range(0, height):
    line=""
    for j in range(0,width):
        if(i == 0 or i == height -1):
            line = line + "13 "
        elif(j == 0 or j == width - 1):
            line += "13 "
        elif( j==4 and i == height -10):
            line += "17 "
        elif (i== height-4 and j == width - 10):
            line += "300 "
        elif( random.randint(0,10)< 2):
            line += "101 "
        else:
           line += "0  "
    print line
