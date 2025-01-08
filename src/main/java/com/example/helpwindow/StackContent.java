package com.example.helpwindow;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class StackContent extends VBox {

    public StackContent() {

        Heading heading = new Heading("STACK");
        FirstHeading firstHeading = new FirstHeading("Introduction to Stack");
        NormalText normalText = new NormalText("A stack is a linear data structure that follows the Last In, First Out (LIFO) principle. This means the element that is inserted last is the one that is removed first. It is commonly visualized as a stack of plates: the last plate placed on the stack is the first one to be removed.", this);
        SecondHeading secondHeading = new SecondHeading("Key Operations");
        NormalText normalText1 = new NormalText("""
                • Push: Adds an element to the top of the stack.
                • Pop: Removes the element from the top of the stack.
                • Peek/Top: Returns the element at the top of the stack without removing it.
                • isEmpty: Checks whether the stack is empty.
                • Size: Returns the number of elements in the stack.
                """, this);
        FirstHeading firstHeading1 = new FirstHeading("Key Properties of Stack");
        NormalText normalText2 = new NormalText("""
                • LIFO: Last In First Out.
                • Fixed Size: In some implementations (like arrays), stacks have a fixed size.
                • Dynamic Size: In linked list implementations, the size of the stack can grow dynamically as elements are added.
                """, this);
        FirstHeading firstHeading2 = new FirstHeading("Applications of Stack");
        NormalText normalText3 = new NormalText("""
                Stacks are used in various applications such as:
                
                    • Expression Evaluation: Converting infix expressions to postfix, evaluating postfix expressions, etc.
                    • Backtracking Algorithms: Implementing undo functionality, depth-first search (DFS) in graphs, etc.
                    • Function Call Management: The call stack in a program maintains the function calls and returns.
                """, this);
        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.getStyleClass().add("separator");
        FirstHeading firstHeading3 = new FirstHeading("Types of Stack Implementations");
        SecondHeading secondHeading1 = new SecondHeading("Array based Stack Implementation");
        NormalText normalText4 = new NormalText("In an array-based implementation, we use a simple array to store the elements of the stack. Here is the Java code for implementing a stack using an array:", this);
        Code code = new Code("""
                class StackArray {
                    private int[] stack;
                    private int top;
                    private int capacity;
                
                    public StackArray(int size) {
                        capacity = size;
                        stack = new int[size];
                        top = -1;
                    }
                
                    // Push operation
                    public void push(int item) {
                        if (top == capacity - 1) {
                            System.out.println("Stack Overflow");
                        } else {
                            stack[++top] = item;
                            System.out.println("Pushed: " + item);
                        }
                    }
                
                    // Pop operation
                    public int pop() {
                        if (top == -1) {
                            System.out.println("Stack Underflow");
                            return -1;
                        } else {
                            return stack[top--];
                        }
                    }
                
                    // Peek operation
                    public int peek() {
                        if (top == -1) {
                            System.out.println("Stack is empty");
                            return -1;
                        } else {
                            return stack[top];
                        }
                    }
                
                    // Check if the stack is empty
                    public boolean isEmpty() {
                        return top == -1;
                    }
                
                    // Get the size of the stack
                    public int size() {
                        return top + 1;
                    }
                }
                """,
                """
                        class StackArray {
                        private:
                            int* stack;
                            int top;
                            int capacity;
                        
                        public:
                            StackArray(int size) {
                                capacity = size;
                                stack = new int[size];
                                top = -1;
                            }
                        
                            // Push operation
                            void push(int item) {
                                if (top == capacity - 1) {
                                    cout << "Stack Overflow" << endl;
                                } else {
                                    stack[++top] = item;
                                    cout << "Pushed: " << item << endl;
                                }
                            }
                        
                            // Pop operation
                            int pop() {
                                if (top == -1) {
                                    cout << "Stack Underflow" << endl;
                                    return -1;
                                } else {
                                    return stack[top--];
                                }
                            }
                        
                            // Peek operation
                            int peek() {
                                if (top == -1) {
                                    cout << "Stack is empty" << endl;
                                    return -1;
                                } else {
                                    return stack[top];
                                }
                            }
                        
                            // Check if the stack is empty
                            bool isEmpty() {
                                return top == -1;
                            }
                        
                            // Get the size of the stack
                            int size() {
                                return top + 1;
                            }
                        
                            // Destructor to free the allocated memory
                            ~StackArray() {
                                delete[] stack;
                            }
                        };
                        """,
                """
                        class StackArray:
                            def __init__(self, size):
                                self.capacity = size
                                self.stack = [0] * size
                                self.top = -1
                        
                            # Push operation
                            def push(self, item):
                                if self.top == self.capacity - 1:
                                    print("Stack Overflow")
                                else:
                                    self.top += 1
                                    self.stack[self.top] = item
                                    print(f"Pushed: {item}")
                        
                            # Pop operation
                            def pop(self):
                                if self.top == -1:
                                    print("Stack Underflow")
                                    return -1
                                else:
                                    item = self.stack[self.top]
                                    self.top -= 1
                                    return item
                        
                            # Peek operation
                            def peek(self):
                                if self.top == -1:
                                    print("Stack is empty")
                                    return -1
                                else:
                                    return self.stack[self.top]
                        
                            # Check if the stack is empty
                            def is_empty(self):
                                return self.top == -1
                        
                            # Get the size of the stack
                            def size(self):
                                return self.top + 1
                        """
        );
        SecondHeading secondHeading2 = new SecondHeading("Linked List based Stack Implementation");
        NormalText normalText5 = new NormalText("In a linked list-based stack, each element is stored in a node, and the top of the stack is represented by the head node of the linked list. Here is the Java code for a linked list-based stack implementation:", this);
        Code code1 = new Code("""
                class StackLinkedList {
                    private Node top;
                
                    // Node class to represent each element
                    private static class Node {
                        int data;
                        Node next;
                
                        Node(int data) {
                            this.data = data;
                            next = null;
                        }
                    }
                
                    // Push operation
                    public void push(int data) {
                        Node newNode = new Node(data);
                        newNode.next = top;
                        top = newNode;
                        System.out.println("Pushed: " + data);
                    }
                
                    // Pop operation
                    public int pop() {
                        if (top == null) {
                            System.out.println("Stack Underflow");
                            return -1;
                        } else {
                            int poppedData = top.data;
                            top = top.next;
                            return poppedData;
                        }
                    }
                
                    // Peek operation
                    public int peek() {
                        if (top == null) {
                            System.out.println("Stack is empty");
                            return -1;
                        } else {
                            return top.data;
                        }
                    }
                
                    // Check if the stack is empty
                    public boolean isEmpty() {
                        return top == null;
                    }
                
                    // Get the size of the stack
                    public int size() {
                        int size = 0;
                        Node current = top;
                        while (current != null) {
                            size++;
                            current = current.next;
                        }
                        return size;
                    }
                }
                """,
                """
                        #include <iostream>
                        using namespace std;
                        
                        class StackLinkedList {
                        private:
                            // Node class to represent each element
                            class Node {
                            public:
                                int data;
                                Node* next;
                        
                                Node(int data) {
                                    this->data = data;
                                    this->next = nullptr;
                                }
                            };
                        
                            Node* top;
                        
                        public:
                            StackLinkedList() {
                                top = nullptr;
                            }
                        
                            // Push operation
                            void push(int data) {
                                Node* newNode = new Node(data);
                                newNode->next = top;
                                top = newNode;
                                cout << "Pushed: " << data << endl;
                            }
                        
                            // Pop operation
                            int pop() {
                                if (top == nullptr) {
                                    cout << "Stack Underflow" << endl;
                                    return -1;
                                } else {
                                    int poppedData = top->data;
                                    Node* temp = top;
                                    top = top->next;
                                    delete temp;
                                    return poppedData;
                                }
                            }
                        
                            // Peek operation
                            int peek() {
                                if (top == nullptr) {
                                    cout << "Stack is empty" << endl;
                                    return -1;
                                } else {
                                    return top->data;
                                }
                            }
                        
                            // Check if the stack is empty
                            bool isEmpty() {
                                return top == nullptr;
                            }
                        
                            // Get the size of the stack
                            int size() {
                                int size = 0;
                                Node* current = top;
                                while (current != nullptr) {
                                    size++;
                                    current = current->next;
                                }
                                return size;
                            }
                        
                            // Destructor to free the allocated memory
                            ~StackLinkedList() {
                                while (top != nullptr) {
                                    pop();
                                }
                            }
                        };
                        
                        """,
                """
                        class StackLinkedList:
                            # Node class to represent each element
                            class Node:
                                def __init__(self, data):
                                    self.data = data
                                    self.next = None
                        
                            def __init__(self):
                                self.top = None
                        
                            # Push operation
                            def push(self, data):
                                new_node = self.Node(data)
                                new_node.next = self.top
                                self.top = new_node
                                print(f"Pushed: {data}")
                        
                            # Pop operation
                            def pop(self):
                                if self.top is None:
                                    print("Stack Underflow")
                                    return -1
                                else:
                                    popped_data = self.top.data
                                    self.top = self.top.next
                                    return popped_data
                        
                            # Peek operation
                            def peek(self):
                                if self.top is None:
                                    print("Stack is empty")
                                    return -1
                                else:
                                    return self.top.data
                        
                            # Check if the stack is empty
                            def is_empty(self):
                                return self.top is None
                        
                            # Get the size of the stack
                            def size(self):
                                size = 0
                                current = self.top
                                while current:
                                    size += 1
                                    current = current.next
                                return size
                        
                        """
        );
        FirstHeading firstHeading4 = new FirstHeading("Advanced Concepts in Stack");
        SecondHeading secondHeading3 = new SecondHeading("Applications of Stack");
        NormalText normalText6 = new NormalText("""
                Expression Evaluation: Stacks are used to evaluate arithmetic expressions, particularly in postfix and infix expressions. For example:
                
                    • Infix to Postfix conversion
                    • Postfix Expression Evaluation
                
                Example of postfix evaluation:
                """, this);
        Code code2 = new Code("""
                public int evaluatePostfix(String exp) {
                    StackArray stack = new StackArray(exp.length());
                    for (char c : exp.toCharArray()) {
                        if (Character.isDigit(c)) {
                            stack.push(c - '0');
                        } else {
                            int operand2 = stack.pop();
                            int operand1 = stack.pop();
                            switch (c) {
                                case '+':
                                    stack.push(operand1 + operand2);
                                    break;
                                case '-':
                                    stack.push(operand1 - operand2);
                                    break;
                                case '*':
                                    stack.push(operand1 * operand2);
                                    break;
                                case '/':
                                    stack.push(operand1 / operand2);
                                    break;
                            }
                        }
                    }
                    return stack.pop();
                }
                """,
                """
                        #include <iostream>
                        #include <string>
                        #include <cctype>
                        using namespace std;
                        
                        class StackArray {
                        private:
                            int* stack;
                            int top;
                            int capacity;
                        
                        public:
                            StackArray(int size) {
                                capacity = size;
                                stack = new int[size];
                                top = -1;
                            }
                        
                            void push(int item) {
                                if (top == capacity - 1) {
                                    cout << "Stack Overflow" << endl;
                                } else {
                                    stack[++top] = item;
                                }
                            }
                        
                            int pop() {
                                if (top == -1) {
                                    cout << "Stack Underflow" << endl;
                                    return -1;
                                } else {
                                    return stack[top--];
                                }
                            }
                        
                            ~StackArray() {
                                delete[] stack;
                            }
                        };
                        
                        int evaluatePostfix(const string& exp) {
                            StackArray stack(exp.length());
                            for (char c : exp) {
                                if (isdigit(c)) {
                                    stack.push(c - '0');
                                } else {
                                    int operand2 = stack.pop();
                                    int operand1 = stack.pop();
                                    switch (c) {
                                        case '+':
                                            stack.push(operand1 + operand2);
                                            break;
                                        case '-':
                                            stack.push(operand1 - operand2);
                                            break;
                                        case '*':
                                            stack.push(operand1 * operand2);
                                            break;
                                        case '/':
                                            stack.push(operand1 / operand2);
                                            break;
                                    }
                                }
                            }
                            return stack.pop();
                        }
                        """,
                """
                        class StackArray:
                            def __init__(self, size):
                                self.capacity = size
                                self.stack = [0] * size
                                self.top = -1
                        
                            def push(self, item):
                                if self.top == self.capacity - 1:
                                    print("Stack Overflow")
                                else:
                                    self.top += 1
                                    self.stack[self.top] = item
                        
                            def pop(self):
                                if self.top == -1:
                                    print("Stack Underflow")
                                    return -1
                                else:
                                    item = self.stack[self.top]
                                    self.top -= 1
                                    return item
                        
                        def evaluate_postfix(exp):
                            stack = StackArray(len(exp))
                            for c in exp:
                                if c.isdigit():
                                    stack.push(int(c))
                                else:
                                    operand2 = stack.pop()
                                    operand1 = stack.pop()
                                    if c == '+':
                                        stack.push(operand1 + operand2)
                                    elif c == '-':
                                        stack.push(operand1 - operand2)
                                    elif c == '*':
                                        stack.push(operand1 * operand2)
                                    elif c == '/':
                                        stack.push(operand1 / operand2)
                            return stack.pop()
                        """
                );
        NormalText normalText7 = new NormalText("""
                • Function Call Stack (Stack Frames): The call stack is used to keep track of function calls in most programming languages. Each time a function is called, a new "frame" is pushed onto the call stack. When the function completes, the frame is popped.
                
                • Undo Mechanism: The stack is often used for undo functionality in applications. Each action performed (e.g., typing a letter, drawing on a canvas) is pushed onto the stack, and when the user presses Undo, the action is popped from the stack.
                """, this);
        SecondHeading secondHeading4 = new SecondHeading("Stack Memory vs Heap Memory");
        NormalText normalText8 = new NormalText("""
                • Stack Memory: The memory that is used to store method calls, local variables, and function calls.
                • Heap Memory: Used for dynamic memory allocation, such as creating objects.
                """, this);
        SecondHeading secondHeading5 = new SecondHeading("Recursive Algorithms");
        NormalText normalText9 = new NormalText("In recursive algorithms, the call stack is used to store the function calls. Each recursive call pushes a new stack frame onto the call stack, and once the base condition is reached, the function starts to return, popping each frame off the stack.", this);
        FirstHeading firstHeading5 = new FirstHeading("Complexity Analysis");
        NormalText normalText10 = new NormalText("""
                • Push Operation: O(1) in both array-based and linked-list-based stacks.
                • Pop Operation: O(1) in both array-based and linked-list-based stacks.
                • Peek Operation: O(1).
                • Size: O(n) for the linked-list implementation, O(1) for the array implementation (since it keeps track of the top index).
                """, this);
        FirstHeading firstHeading6 = new FirstHeading("Pros and Cons");
        SecondHeading secondHeading6 = new SecondHeading("Array based Stack");
        NormalText normalText11 = new NormalText("""
                • Pros:
                
                    • Simple to implement.
                    • Fixed size is known in advance.
                
                • Cons:
                
                    • Fixed size can lead to wasted space or overflow if the stack grows beyond the array’s capacity.
                """, this);
        SecondHeading secondHeading7 = new SecondHeading("Linked list based Stack");
        NormalText normalText12 = new NormalText("""
                • Pros:
                
                    • Dynamic size, no overflow issues.
                    • More flexible.
                
                • Cons:
                
                    • Additional overhead due to node creation.
                    • Slower access to elements compared to an array.
                """, this);
        this.getChildren().addAll(heading, firstHeading, normalText, secondHeading, normalText1, firstHeading1, normalText2, firstHeading2, normalText3, separator,
                firstHeading3, secondHeading1, normalText4, code, secondHeading2, normalText5, code1, firstHeading4, secondHeading3, normalText6, code2, normalText7,
                secondHeading4, normalText8, secondHeading5, normalText9, firstHeading5, normalText10, firstHeading6, secondHeading6, normalText11, secondHeading7, normalText12);
        this.getStyleClass().add("root");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/helpwindow/content.css")).toExternalForm());

    }
}
