package com.example.helpwindow;

import javafx.scene.layout.VBox;

import java.util.Objects;

public class QueueContent extends VBox {

    public QueueContent() {

        Heading heading = new Heading("QUEUE");
        FirstHeading firstHeading = new FirstHeading("Basic Definition");
        NormalText normalText = new NormalText("""
                A Queue is a linear data structure that follows the First In First Out (FIFO) principle, meaning the element that is inserted first is the first one to be removed. It is similar to a real-world queue, such as a line at a supermarket or a ticket counter, where people enter from the rear and leave from the front.
                """, this);
        NormalText normalText1 = new NormalText("""
                • Enqueue: The operation of adding an element to the end (rear) of the queue.
                • Dequeue: The operation of removing an element from the front of the queue.
                • Peek/Front: The operation to view the element at the front of the queue without removing it.
                • IsEmpty: Checks if the queue has any elements.
                • Size: Returns the number of elements in the queue.
                """, this);
        SecondHeading secondHeading = new SecondHeading("Queue Operations");
        NormalText normalText2 = new NormalText("""
                • Enqueue (Insertion)
                • Dequeue (Removal)
                • Peek (View the front element)
                • isEmpty (Check if the queue is empty)
                • Size (Get the size of the queue)
                """, this);
        FirstHeading firstHeading1 = new FirstHeading("Types of Queue");
        NormalText normalText3 = new NormalText("Queues can be implemented in different forms based on the operations or the structure used to store elements.", this);
        SecondHeading secondHeading1 = new SecondHeading("Linear Queue(Simple Queue)");
        NormalText normalText4 = new NormalText("A basic implementation where the queue is implemented using arrays or linked lists. In this case, once an element is removed from the front, there is no way to reuse that space unless the entire queue is shifted.", this);
        SecondHeading secondHeading2 = new SecondHeading("Circular Queue");
        NormalText normalText5 = new NormalText("A Circular Queue is an improvement over the simple queue. In a circular queue, the last position is connected to the first position, and it wraps around. This eliminates the need to shift elements when the front is dequeued.", this);
        NormalText normalText6 = new NormalText("""
                • The rear pointer wraps around to the front once it reaches the end.
                • Array-based Circular Queue uses modulo arithmetic to handle wrapping.
                """, this);
        SecondHeading secondHeading3 = new SecondHeading("Double Ended Queue(Deque)");
        NormalText normalText7 = new NormalText("A Deque is a generalized version of the queue where insertion and deletion can happen at both ends (front and rear). Deques can be classified into:", this);
        NormalText normalText8 = new NormalText("""
                • Input Restricted Deque: Allows insertions at the rear only and deletions from both ends.
                • Output Restricted Deque: Allows insertions at the front only and deletions from both ends.
                """, this);
        SecondHeading secondHeading4 = new SecondHeading("Priority Queue");
        NormalText normalText9 = new NormalText("A Priority Queue is a special type of queue where each element has a priority. Elements with higher priority are dequeued before elements with lower priority, regardless of the order in which they were added. This is often implemented using a heap (binary heap), where the highest (or lowest) priority element is always at the root.", this);
        NormalText normalText10 = new NormalText("""
                • Max Priority Queue: The element with the highest priority is dequeued first.
                • Min Priority Queue: The element with the lowest priority is dequeued first.
                """, this);
        FirstHeading firstHeading2 = new FirstHeading("Applications of Queue");
        NormalText normalText11 = new NormalText("Queues are widely used in real-world applications and computer science problems, including:", this);
        NormalText normalText12 = new NormalText("""
                • Task Scheduling: Queues are used in scheduling tasks in operating systems (e.g., CPU scheduling).
                • Breadth-First Search (BFS): BFS algorithm uses a queue to explore graph nodes level by level.
                • Buffers: Queues are used in buffers like IO buffers, print queues, and network buffers, where data is processed in the order it arrives.
                • Asynchronous Data Transfer: In data communication, messages are placed in a queue to be transmitted in order.
                """, this);
        FirstHeading firstHeading3 = new FirstHeading("Queue Implementation");
        NormalText normalText13 = new NormalText("Queues can be implemented using different data structures such as arrays, linked lists, or even stacks. Let's discuss a few approaches.", this);
        SecondHeading secondHeading5 = new SecondHeading("Array Based Queue");
        NormalText normalText14 = new NormalText("In an array-based queue, two pointers (front and rear) are used to keep track of the start and end of the queue.", this);
        NormalText normalText15 = new NormalText("""
                • Enqueue operation: Increment the rear pointer and insert the element.
                • Dequeue operation: Increment the front pointer and remove the element.
                """, this);
        Code code = new Code("""
                // Queue Implementation using Array
                 class QueueArray {
                     private int[] queue;
                     private int front, rear, size, capacity;
                
                     // Constructor to initialize the queue
                     public QueueArray(int capacity) {
                         this.capacity = capacity;
                         this.queue = new int[capacity];
                         this.front = this.rear = -1;
                         this.size = 0;
                     }
                
                     // Enqueue operation (add element to the rear)
                     public void enqueue(int value) {
                         if (size == capacity) {
                             System.out.println("Queue is full");
                             return;
                         }
                         if (front == -1) {
                             front = 0; // First element inserted
                         }
                         rear = (rear + 1) % capacity; // Circular increment
                         queue[rear] = value;
                         size++;
                     }
                
                     // Dequeue operation (remove element from the front)
                     public int dequeue() {
                         if (size == 0) {
                             System.out.println("Queue is empty");
                             return -1;
                         }
                         int value = queue[front];
                         front = (front + 1) % capacity; // Circular increment
                         size--;
                         return value;
                     }
                
                     // Peek operation (view the element at the front)
                     public int peek() {
                         if (size == 0) {
                             System.out.println("Queue is empty");
                             return -1;
                         }
                         return queue[front];
                     }
                
                     // Check if the queue is empty
                     public boolean isEmpty() {
                         return size == 0;
                     }
                
                     // Check if the queue is full
                     public boolean isFull() {
                         return size == capacity;
                     }
                
                     // Get the size of the queue
                     public int size() {
                         return size;
                     }
                 }
                
                 // Test the QueueArray class
                 public class Main {
                     public static void main(String[] args) {
                         QueueArray queue = new QueueArray(5);
                
                         queue.enqueue(10);
                         queue.enqueue(20);
                         queue.enqueue(30);
                         queue.enqueue(40);
                         queue.enqueue(50);
                
                         System.out.println("Front element: " + queue.peek());
                         System.out.println("Dequeued: " + queue.dequeue());
                         System.out.println("Dequeued: " + queue.dequeue());
                         System.out.println("Queue size: " + queue.size());
                         System.out.println("Is queue empty? " + queue.isEmpty());
                     }
                 }
                """,
                """
                        #include <iostream>
                        using namespace std;
                        
                        class QueueArray {
                        private:
                            int* queue;
                            int front, rear, size, capacity;
                        
                        public:
                            QueueArray(int capacity) {
                                this->capacity = capacity;
                                queue = new int[capacity];
                                front = rear = -1;
                                size = 0;
                            }
                        
                            void enqueue(int value) {
                                if (size == capacity) {
                                    cout << "Queue is full" << endl;
                                    return;
                                }
                                if (front == -1) {
                                    front = 0; // First element inserted
                                }
                                rear = (rear + 1) % capacity; // Circular increment
                                queue[rear] = value;
                                size++;
                            }
                        
                            int dequeue() {
                                if (size == 0) {
                                    cout << "Queue is empty" << endl;
                                    return -1;
                                }
                                int value = queue[front];
                                front = (front + 1) % capacity; // Circular increment
                                size--;
                                return value;
                            }
                        
                            int peek() {
                                if (size == 0) {
                                    cout << "Queue is empty" << endl;
                                    return -1;
                                }
                                return queue[front];
                            }
                        
                            bool isEmpty() {
                                return size == 0;
                            }
                        
                            bool isFull() {
                                return size == capacity;
                            }
                        
                            int getSize() {
                                return size;
                            }
                        
                            ~QueueArray() {
                                delete[] queue;
                            }
                        };
                        
                        int main() {
                            QueueArray queue(5);
                        
                            queue.enqueue(10);
                            queue.enqueue(20);
                            queue.enqueue(30);
                            queue.enqueue(40);
                            queue.enqueue(50);
                        
                            cout << "Front element: " << queue.peek() << endl;
                            cout << "Dequeued: " << queue.dequeue() << endl;
                            cout << "Dequeued: " << queue.dequeue() << endl;
                            cout << "Queue size: " << queue.getSize() << endl;
                            cout << "Is queue empty? " << (queue.isEmpty() ? "Yes" : "No") << endl;
                        
                            return 0;
                        }
                        
                        """,
                """
                        class QueueArray:
                            def __init__(self, capacity):
                                self.capacity = capacity
                                self.queue = [0] * capacity
                                self.front = self.rear = -1
                                self.size = 0
                        
                            def enqueue(self, value):
                                if self.size == self.capacity:
                                    print("Queue is full")
                                    return
                                if self.front == -1:
                                    self.front = 0  # First element inserted
                                self.rear = (self.rear + 1) % self.capacity  # Circular increment
                                self.queue[self.rear] = value
                                self.size += 1
                        
                            def dequeue(self):
                                if self.size == 0:
                                    print("Queue is empty")
                                    return -1
                                value = self.queue[self.front]
                                self.front = (self.front + 1) % self.capacity  # Circular increment
                                self.size -= 1
                                return value
                        
                            def peek(self):
                                if self.size == 0:
                                    print("Queue is empty")
                                    return -1
                                return self.queue[self.front]
                        
                            def is_empty(self):
                                return self.size == 0
                        
                            def is_full(self):
                                return self.size == self.capacity
                        
                            def get_size(self):
                                return self.size
                        
                        # Test the QueueArray class
                        queue = QueueArray(5)
                        
                        queue.enqueue(10)
                        queue.enqueue(20)
                        queue.enqueue(30)
                        queue.enqueue(40)
                        queue.enqueue(50)
                        
                        print("Front element:", queue.peek())
                        print("Dequeued:", queue.dequeue())
                        print("Dequeued:", queue.dequeue())
                        print("Queue size:", queue.get_size())
                        print("Is queue empty?", "Yes" if queue.is_empty() else "No")
                        """
        );
        NormalText normalText16 = new NormalText("The disadvantage is that once the array fills up, there’s no space reuse unless the entire queue is shifted. This inefficiency can be avoided by implementing a circular queue.", this);
        SecondHeading secondHeading6 = new SecondHeading("Linked List Based Queue");
        NormalText normalText17 = new NormalText("A linked list-based queue uses a linked list where elements are added to the rear and removed from the front. This allows dynamic memory allocation and avoids the problem of shifting elements.", this);
        NormalText normalText18 = new NormalText("""
                • Enqueue operation: Add a new node at the rear.
                • Dequeue operation: Remove the node from the front.
                """, this);
        Code code1 = new Code("""
                // Node class to represent each element in the queue
                class Node {
                    int data;
                    Node next;
                
                    public Node(int data) {
                        this.data = data;
                        this.next = null;
                    }
                }
                
                // Queue Implementation using Linked List
                class QueueLinkedList {
                    private Node front, rear;
                    private int size;
                
                    // Constructor to initialize the queue
                    public QueueLinkedList() {
                        this.front = this.rear = null;
                        this.size = 0;
                    }
                
                    // Enqueue operation (add element to the rear)
                    public void enqueue(int value) {
                        Node newNode = new Node(value);
                        if (rear == null) {
                            front = rear = newNode; // Queue is empty
                        } else {
                            rear.next = newNode; // Add to the end of the queue
                            rear = newNode;
                        }
                        size++;
                    }
                
                    // Dequeue operation (remove element from the front)
                    public int dequeue() {
                        if (front == null) {
                            System.out.println("Queue is empty");
                            return -1;
                        }
                        int value = front.data;
                        front = front.next; // Remove the front node
                        if (front == null) {
                            rear = null; // Queue is now empty
                        }
                        size--;
                        return value;
                    }
                
                    // Peek operation (view the element at the front)
                    public int peek() {
                        if (front == null) {
                            System.out.println("Queue is empty");
                            return -1;
                        }
                        return front.data;
                    }
                
                    // Check if the queue is empty
                    public boolean isEmpty() {
                        return front == null;
                    }
                
                    // Get the size of the queue
                    public int size() {
                        return size;
                    }
                }
                
                // Test the QueueLinkedList class
                public class Main {
                    public static void main(String[] args) {
                        QueueLinkedList queue = new QueueLinkedList();
                
                        queue.enqueue(10);
                        queue.enqueue(20);
                        queue.enqueue(30);
                        queue.enqueue(40);
                        queue.enqueue(50);
                
                        System.out.println("Front element: " + queue.peek());
                        System.out.println("Dequeued: " + queue.dequeue());
                        System.out.println("Dequeued: " + queue.dequeue());
                        System.out.println("Queue size: " + queue.size());
                        System.out.println("Is queue empty? " + queue.isEmpty());
                    }
                }
                """,
                """
                        #include <iostream>
                        using namespace std;
                        
                        class Node {
                        public:
                            int data;
                            Node* next;
                        
                            Node(int data) {
                                this->data = data;
                                this->next = nullptr;
                            }
                        };
                        
                        class QueueLinkedList {
                        private:
                            Node* front;
                            Node* rear;
                            int size;
                        
                        public:
                            QueueLinkedList() {
                                front = rear = nullptr;
                                size = 0;
                            }
                        
                            void enqueue(int value) {
                                Node* newNode = new Node(value);
                                if (rear == nullptr) {
                                    front = rear = newNode; // Queue is empty
                                } else {
                                    rear->next = newNode; // Add to the end of the queue
                                    rear = newNode;
                                }
                                size++;
                            }
                        
                            int dequeue() {
                                if (front == nullptr) {
                                    cout << "Queue is empty" << endl;
                                    return -1;
                                }
                                int value = front->data;
                                front = front->next; // Remove the front node
                                if (front == nullptr) {
                                    rear = nullptr; // Queue is now empty
                                }
                                size--;
                                return value;
                            }
                        
                            int peek() {
                                if (front == nullptr) {
                                    cout << "Queue is empty" << endl;
                                    return -1;
                                }
                                return front->data;
                            }
                        
                            bool isEmpty() {
                                return front == nullptr;
                            }
                        
                            int getSize() {
                                return size;
                            }
                        };
                        
                        int main() {
                            QueueLinkedList queue;
                        
                            queue.enqueue(10);
                            queue.enqueue(20);
                            queue.enqueue(30);
                            queue.enqueue(40);
                            queue.enqueue(50);
                        
                            cout << "Front element: " << queue.peek() << endl;
                            cout << "Dequeued: " << queue.dequeue() << endl;
                            cout << "Dequeued: " << queue.dequeue() << endl;
                            cout << "Queue size: " << queue.getSize() << endl;
                            cout << "Is queue empty? " << (queue.isEmpty() ? "Yes" : "No") << endl;
                        
                            return 0;
                        }
                        """,
                """
                        class Node:
                            def __init__(self, data):
                                self.data = data
                                self.next = None
                        
                        class QueueLinkedList:
                            def __init__(self):
                                self.front = self.rear = None
                                self.size = 0
                        
                            def enqueue(self, value):
                                new_node = Node(value)
                                if self.rear is None:
                                    self.front = self.rear = new_node  # Queue is empty
                                else:
                                    self.rear.next = new_node  # Add to the end of the queue
                                    self.rear = new_node
                                self.size += 1
                        
                            def dequeue(self):
                                if self.front is None:
                                    print("Queue is empty")
                                    return -1
                                value = self.front.data
                                self.front = self.front.next  # Remove the front node
                                if self.front is None:
                                    self.rear = None  # Queue is now empty
                                self.size -= 1
                                return value
                        
                            def peek(self):
                                if self.front is None:
                                    print("Queue is empty")
                                    return -1
                                return self.front.data
                        
                            def is_empty(self):
                                return self.front is None
                        
                            def get_size(self):
                                return self.size
                        
                        # Test the QueueLinkedList class
                        queue = QueueLinkedList()
                        
                        queue.enqueue(10)
                        queue.enqueue(20)
                        queue.enqueue(30)
                        queue.enqueue(40)
                        queue.enqueue(50)
                        
                        print("Front element:", queue.peek())
                        print("Dequeued:", queue.dequeue())
                        print("Dequeued:", queue.dequeue())
                        print("Queue size:", queue.get_size())
                        print("Is queue empty?", "Yes" if queue.is_empty() else "No")
                        """
        );
        NormalText normalText19 = new NormalText("This approach eliminates the fixed-size limitation of array-based queues.", this);
        FirstHeading firstHeading4 = new FirstHeading("Circular Queue");
        NormalText normalText20 = new NormalText("To overcome the problem of wasted space in a simple array queue (due to the non-reuse of space from dequeued elements), a circular queue can be implemented. Here’s how it works:", this);
        NormalText normalText21 = new NormalText("""
                • Queue array: Instead of shifting elements after dequeueing, the array "wraps around".
                • Modulo operation: The rear and front pointers are handled using modulo arithmetic to keep them within the array bounds.
                """, this);
        Code code2 = new Code("""
                class CircularQueue {
                     private int[] queue;
                     private int front, rear, size, capacity;
                
                     public CircularQueue(int capacity) {
                         this.capacity = capacity;
                         this.queue = new int[capacity];
                         this.front = this.rear = -1;
                         this.size = 0;
                     }
                
                     // Enqueue operation
                     public void enqueue(int value) {
                         if (size == capacity) {
                             System.out.println("Queue is full");
                             return;
                         }
                         rear = (rear + 1) % capacity;
                         queue[rear] = value;
                         if (front == -1) {
                             front = 0;
                         }
                         size++;
                     }
                
                     // Dequeue operation
                     public int dequeue() {
                         if (size == 0) {
                             System.out.println("Queue is empty");
                             return -1;
                         }
                         int value = queue[front];
                         front = (front + 1) % capacity;
                         size--;
                         return value;
                     }
                
                     // Peek operation
                     public int peek() {
                         if (size == 0) {
                             System.out.println("Queue is empty");
                             return -1;
                         }
                         return queue[front];
                     }
                
                     public boolean isEmpty() {
                         return size == 0;
                     }
                
                     public boolean isFull() {
                         return size == capacity;
                     }
                 }
                """,
                """
                        #include <iostream>
                        using namespace std;
                        
                        class CircularQueue {
                        private:
                            int* queue;
                            int front, rear, size, capacity;
                        
                        public:
                            CircularQueue(int capacity) {
                                this->capacity = capacity;
                                this->queue = new int[capacity];
                                this->front = this->rear = -1;
                                this->size = 0;
                            }
                        
                            // Enqueue operation
                            void enqueue(int value) {
                                if (size == capacity) {
                                    cout << "Queue is full" << endl;
                                    return;
                                }
                                rear = (rear + 1) % capacity;
                                queue[rear] = value;
                                if (front == -1) {
                                    front = 0;
                                }
                                size++;
                            }
                        
                            // Dequeue operation
                            int dequeue() {
                                if (size == 0) {
                                    cout << "Queue is empty" << endl;
                                    return -1;
                                }
                                int value = queue[front];
                                front = (front + 1) % capacity;
                                size--;
                                return value;
                            }
                        
                            // Peek operation
                            int peek() {
                                if (size == 0) {
                                    cout << "Queue is empty" << endl;
                                    return -1;
                                }
                                return queue[front];
                            }
                        
                            bool isEmpty() {
                                return size == 0;
                            }
                        
                            bool isFull() {
                                return size == capacity;
                            }
                        };
                        
                        int main() {
                            CircularQueue queue(5);
                        
                            queue.enqueue(10);
                            queue.enqueue(20);
                            queue.enqueue(30);
                            queue.enqueue(40);
                            queue.enqueue(50);
                        
                            cout << "Front element: " << queue.peek() << endl;
                            cout << "Dequeued: " << queue.dequeue() << endl;
                            cout << "Dequeued: " << queue.dequeue() << endl;
                            cout << "Queue size: " << queue.isEmpty() << endl;
                            cout << "Is queue full? " << (queue.isFull() ? "Yes" : "No") << endl;
                        
                            return 0;
                        }
                        """,
                """
                        class CircularQueue:
                            def __init__(self, capacity):
                                self.capacity = capacity
                                self.queue = [None] * capacity
                                self.front = self.rear = -1
                                self.size = 0
                        
                            # Enqueue operation
                            def enqueue(self, value):
                                if self.size == self.capacity:
                                    print("Queue is full")
                                    return
                                self.rear = (self.rear + 1) % self.capacity
                                self.queue[self.rear] = value
                                if self.front == -1:
                                    self.front = 0
                                self.size += 1
                        
                            # Dequeue operation
                            def dequeue(self):
                                if self.size == 0:
                                    print("Queue is empty")
                                    return -1
                                value = self.queue[self.front]
                                self.front = (self.front + 1) % self.capacity
                                self.size -= 1
                                return value
                        
                            # Peek operation
                            def peek(self):
                                if self.size == 0:
                                    print("Queue is empty")
                                    return -1
                                return self.queue[self.front]
                        
                            def is_empty(self):
                                return self.size == 0
                        
                            def is_full(self):
                                return self.size == self.capacity
                        
                        
                        # Test the CircularQueue class
                        queue = CircularQueue(5)
                        
                        queue.enqueue(10)
                        queue.enqueue(20)
                        queue.enqueue(30)
                        queue.enqueue(40)
                        queue.enqueue(50)
                        
                        print("Front element:", queue.peek())
                        print("Dequeued:", queue.dequeue())
                        print("Dequeued:", queue.dequeue())
                        print("Queue size:", "Empty" if queue.is_empty() else "Not empty")
                        print("Is queue full?", "Yes" if queue.is_full() else "No")
                        """
        );
        FirstHeading firstHeading5 = new FirstHeading("Queue in Real-World Applications");
        SecondHeading secondHeading7 = new SecondHeading("CPU Scheduling");
        NormalText normalText22 = new NormalText("In operating systems, queues are used for CPU scheduling where processes are added to a ready queue, and the CPU fetches the next process from the front of the queue.", this);
        SecondHeading secondHeading8 = new SecondHeading("BFS(Breadth First Traversal)");
        NormalText normalText23 = new NormalText("In graph traversal algorithms like BFS, a queue is used to keep track of nodes that need to be visited next.", this);
        SecondHeading secondHeading9 = new SecondHeading("Printer Queue");
        NormalText normalText24 = new NormalText("In a printer queue, jobs are printed in the order they were received, and each print job is dequeued when it is processed.", this);
        FirstHeading firstHeading6 = new FirstHeading("Advanced Concepts");
        SecondHeading secondHeading10 = new SecondHeading("Queue as a Deque");
        NormalText normalText25 = new NormalText("A deque (double-ended queue) allows insertion and removal of elements from both the front and rear. It is a generalization of a queue and can be implemented with a doubly linked list.", this);
        SecondHeading secondHeading11 = new SecondHeading("Priority Queue");
        NormalText normalText26 = new NormalText("A priority queue is used when the elements need to be dequeued based on their priority rather than the order in which they were inserted. It’s implemented using heaps, where the highest or lowest priority element is always at the root.", this);

        FirstHeading firstHeading7 = new FirstHeading("Summary of Key Points");
        NormalText normalText27 = new NormalText("""
                • A queue is a FIFO data structure where the first element inserted is the first one to be removed.
                • It can be implemented using arrays, linked lists, or circular queues.
                • Operations: Enqueue, Dequeue, Peek, Size, and IsEmpty.
                • Applications: Task scheduling, BFS, print queues, buffers, etc.
                • Advanced Types: Circular Queue, Deque, and Priority Queue.
                """, this);
        this.getChildren().addAll(heading, firstHeading, normalText, normalText1, secondHeading, normalText2, firstHeading1, normalText3, secondHeading1, normalText4,
                secondHeading2, normalText5, normalText6, secondHeading3, normalText7, normalText8, secondHeading4, normalText9, normalText10, firstHeading2,
                normalText11, normalText12, firstHeading3, normalText13, secondHeading5, normalText14, normalText15, code, normalText16, secondHeading6, normalText17, normalText18,
                code1, normalText19, firstHeading4, normalText20, normalText21, code2, firstHeading5, secondHeading7, normalText22, secondHeading8, normalText23, secondHeading9,
                normalText24, firstHeading6, secondHeading10, normalText25, secondHeading11, normalText26, firstHeading7, normalText27
        );
        this.getStyleClass().add("root");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/helpwindow/content.css")).toExternalForm());
    }
}
