

import csv

from _csv import reader
import matplotlib.pyplot as plt
import matplotlib.patches as patches

import numpy as np
from wx import Height

map = []

with open('./map.csv','r') as csvfile:
    reader = csv.reader(csvfile)
    for row in reader:
        map.append(row)
#print (map)

i = 0
j = 0


fig, ax = plt.subplots()
all_rect = []
for row in map:
    for col in row:
        j = j + 1
        if col == ".":
            continue
        else:
            all_rect.append(plt.Rectangle( (j*1, i*1), width = 1, height = 1 ) )
    j = 0
    i = i + 1
ax.set_xlim(0, 15)
ax.set_ylim(0, 10)

for o in all_rect:
    ax.add_patch(o)
ax.autoscale_view() # 调节坐标范围使矩形能够完全显示
plt.show()


