opposing_pairs = [["{","}"],["[","]"],["<",">"]]

openers = list(zip(*opposing_pairs))[0]
closers = list(zip(*opposing_pairs))[1]

stack = []

line = input("Enter code here: ")

for c in line:
    if c in openers:
        stack.append(c)
    if c in closers:
        if len(stack) == 0:
            print("Stack is empty, too many closers.")
            break
        if not openers[closers.index(c)]==stack[-1]:
            break
        stack.pop(-1)
    print(c,end="")

if len(stack)!=0:
    print("Stack not empty! Unclosed collections exist.")
