import os
import csv


# print(curr_path + '\\tool-inventory.csv')

tool_path = os.getcwd() + '\\tool-inventory.csv'
with open(tool_path, 'r') as csvf:
    reader = csv.reader.next()
    
