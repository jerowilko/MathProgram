droppers = ["<",">"]

line = input("Enter code here: ")

for c in line:
    if not c in droppers:
        print(c,end="")

