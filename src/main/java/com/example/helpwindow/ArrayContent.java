package com.example.helpwindow;

import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class ArrayContent extends VBox {

    public ArrayContent(ContentPane parent) {
        Heading heading = new Heading("ARRAY");
        this.setMaxWidth(parent.viewportBoundsProperty().get().getWidth());
        FirstHeading firstHeading = new FirstHeading("Introduction to Arrays");
        SecondHeading secondHeading = new SecondHeading("Definition");
        NormalText normalText = new NormalText("""
                An array is a linear data structure that stores elements of the same type in contiguous memory locations. Arrays allow random access using indices, making them efficient for certain operations.
                """, this);
        SecondHeading secondHeading1 = new SecondHeading("Key Properties");
        NormalText normalText1 = new NormalText("""
                • Fixed Size: The size of an array is defined at the time of creation and cannot be changed.
                • Homogeneous Elements: All elements in the array must be of the same data type.
                • Indexed Access: Each element is accessed using its index, starting from 0.
                """, this);
        SecondHeading secondHeading2 = new SecondHeading("Initialisation and Declaration");
        Code code = new Code(
                """
                        int[] arr = new int[5];
                        int[] arr2 = {1, 2, 3, 4, 5};
                        """,
                """
                        int arr[5];
                        int arr2[] = {1, 2, 3, 4, 5};
                        """,
                """
                        arr = [0] * 5
                        arr = [1, 2, 3, 4 ,5]
                        """
                );
        FirstHeading firstHeading1 = new FirstHeading("Basic Operations in Arrays");
        SecondHeading secondHeading3 = new SecondHeading("Traversal");
        NormalText normalText2 = new NormalText("Iterate through all elements in the array.", this);
        Code code1 = new Code(
                """
                for (int i = 0; i < arr.length; i++) {
                    System.out.print(arr[i] + " ");
                }
                """,
        """
                for (int i = 0; i < 5; i++) {
                    cout << arr[i] << " ";
                }
                """,
                """
                for num in arr:
                    print(num, end = " ")
                """
        );
        SecondHeading secondHeading4 = new SecondHeading("Insertion");
        NormalText normalText3 = new NormalText("Add an element at a specific position (shifting elements if necessary).", this);
        Code code2 = new Code("""
                int pos = 2;
                int value = 10;
                for (int i = arr.length - 1; i > pos; i--) {
                    arr[i] = arr[i - 1];
                }
                arr[pos] = value;
                """,
                """
                        int pos = 2;
                        int value = 10;
                        for (int i = 4; i > pos; i--) {
                            arr[i] = arr[i - 1];
                        }
                        arr[pos] = value;
                        """,
                """
                        pos = 2
                        value = 10
                        for i in range(len(arr) - 1, pos, -1):
                            arr[i] = arr[i - 1]
                        arr[pos] = value
                        """
        );
        SecondHeading secondHeading5 = new SecondHeading("Deletion");
        NormalText normalText4 = new NormalText("Remove an element from a specific position (shifting elements to fill the gap).", this);
        Code code3 = new Code("""
                int pos = 2;
                for (int i = pos; i < arr.length - 1; i++) {
                    arr[i] = arr[i + 1];
                }
                arr[arr.length - 1] = 0;
                """,
                """
                        int pos = 2;
                        for (int i = pos; i < 4; i++) {
                            arr[i] = arr[i + 1];
                        }
                        arr[4] = 0;
                        """,
                """
                        pos = 2
                        for i in range(pos, len(arr) - 1):
                            arr[i] = arr[i + 1]
                        arr[-1] = 0
                        """
        );
        SecondHeading secondHeading6 = new SecondHeading("Searching");
        NormalText normalText5 = new NormalText("Linear Search: Search each element sequentially (O(n)).", this);
        Code code4 = new Code("""
                int linearSearch(int[] arr, int key) {
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i] == key) {
                            return i;
                        }
                    }
                    return -1;
                }
                """,
                """
                        int linearSearch(int arr[], int size, int key) {
                            for (int i = 0; i < size; i++) {
                                if (arr[i] == key) {
                                    return i;
                                }
                            }
                            return -1;
                        }
                        """,
                """
                        def linearSearch(arr, key):
                            for i in range(len(arr)):
                                if arr[i] == key:
                                    return i
                            return -1
                        """
        );
        NormalText normalText6 = new NormalText("Binary Search: For sorted arrays, search by dividing the array in halves (O(log n)).", this);
        Code code5 = new Code("""
                int binarySearch(int[] arr, int key) {
                     int low = 0, high = arr.length - 1;
                     while (low <= high) {
                         int mid = (low + high) / 2;
                         if (arr[mid] == key) return mid;
                         else if (arr[mid] < key) low = mid + 1;
                         else high = mid - 1;
                     }
                     return -1;
                 }
                """,
                """
                        int binarySearch(int arr[], int size, int key) {
                            int low = 0, high = size - 1;
                            while (low <= high) {
                                int mid = (low + high) / 2;
                                if (arr[mid] == key) return mid;
                                else if (arr[mid] < key) low = mid + 1;
                                else high = mid - 1;
                            }
                            return -1;
                        }
                        """,
                """
                        def binarySearch(arr, key):
                            low, high = 0, len(arr) - 1
                            while low <= high:
                                mid = (low + high) // 2
                                if arr[mid] == key:
                                    return mid
                                elif arr[mid] < key:
                                    low = mid + 1
                                else:
                                    high = mid - 1
                            return -1
                        """
        );
        SecondHeading secondHeading7 = new SecondHeading("Sorting");
        NormalText normalText7 = new NormalText("Algorithms: Bubble Sort, Selection Sort, Insertion Sort, Merge Sort, Quick Sort, etc.", this);
        Code code6 = new Code("""
                for (int i = 0; i < arr.length - 1; i++) {
                    for (int j = 0; j < arr.length - i - 1; j++) {
                        if (arr[j] > arr[j + 1]) {
                            int temp = arr[j];
                            arr[j] = arr[j + 1];
                            arr[j + 1] = temp;
                        }
                    }
                }
                """,
                """
                        for (int i = 0; i < 5 - 1; i++) {
                            for (int j = 0; j < 5 - i - 1; j++) {
                                if (arr[j] > arr[j + 1]) {
                                    int temp = arr[j];
                                    arr[j] = arr[j + 1];
                                    arr[j + 1] = temp;
                                }
                            }
                        }
                        """,
                """
                        for i in range(len(arr) - 1):
                            for j in range(len(arr) - i - 1):
                                if arr[j] > arr[j + 1]:
                                    arr[j], arr[j + 1] = arr[j + 1], arr[j]
                        """
        );
        FirstHeading firstHeading2 = new FirstHeading("Advanced Concepts");
        SecondHeading secondHeading8 = new SecondHeading("Multidimensional Array");
        NormalText normalText8 = new NormalText("Arrays with more than one dimension, such as matrices.", this);
        Code code7 = new Code("""
                int[][] matrix = {
                    {1, 2, 3},
                    {4, 5, 6},
                    {7, 8, 9}
                };
                """,
                """
                        int matrix[3][3] = {
                            {1, 2, 3},
                            {4, 5, 6},
                            {7, 8, 9}
                        };
                        """,
                """
                        matrix = [
                            [1, 2, 3],
                            [4, 5, 6],
                            [7, 8, 9]
                        ]
                        """
        );
        NormalText normalText9 = new NormalText("Accessing Elements", this);
        Code code8 = new Code( """
                System.out.println(matrix[1][2]);, 
                """,
                """
                        cout << matrix[1][2] << endl;
                       """,
                """
                        print(matrix[1][2])
                        """
                );
        SecondHeading secondHeading9 = new SecondHeading("Dynamic Arrays");
        NormalText normalText10 = new NormalText("Arrays with flexible size (not natively supported in Java; use ArrayList or other data structures like Vector).", this);
        Code code9 = new Code("""
                ArrayList<Integer> list = new ArrayList<>();
                list.add(10);
                list.remove(0);
                """,
                """
                        #include <vector>
                        
                        std::vector<int> list;
                        list.push_back(10);
                        list.erase(list.begin());
                        """,
                """
                        list = []
                        list.append(10)
                        list.pop(0)
                        """
        );
        SecondHeading secondHeading10 = new SecondHeading("Sparse Arrays");
        NormalText normalText11 = new NormalText("""
                • Arrays with mostly zero or default values, often represented more compactly using specialized structures.
                • Application: Efficient storage for large datasets (e.g., matrix operations).
                """, this);
        SecondHeading secondHeading11 = new SecondHeading("Jagged Arrays");
        NormalText normalText13 = new NormalText("Arrays where rows can have different lengths.", this);
        Code code10 = new Code("""
                int[][] jaggedArray = new int[3][];
                jaggedArray[0] = new int[2];
                jaggedArray[1] = new int[4];
                jaggedArray[2] = new int[1];
                """,
                """
                        int* jaggedArray[3];
                        jaggedArray[0] = new int[2];
                        jaggedArray[1] = new int[4];
                        jaggedArray[2] = new int[1];
                        """,
                """
                        jaggedArray = [
                            [0] * 2,
                            [0] * 4,
                            [0] * 1
                        ]
                        """
                );
        FirstHeading firstHeading3 = new FirstHeading("Applications of Arrays");
        SecondHeading secondHeading12 = new SecondHeading("Dynamic Programming");
        NormalText normalText14 = new NormalText("Arrays store intermediate results to optimize recursive solutions.", this);
        SecondHeading secondHeading13 = new SecondHeading("Hashing");
        NormalText normalText15 = new NormalText("Arrays can implement hash tables (e.g., as buckets).", this);
        SecondHeading secondHeading14 = new SecondHeading("Graphs");
        NormalText normalText16 = new NormalText("Adjacency matrices or edge lists use arrays to represent graphs.", this);
        SecondHeading secondHeading15 = new SecondHeading("Searching and Sorting");
        NormalText normalText17 = new NormalText("Arrays are foundational for implementing sorting and searching algorithms.", this);

        VBox.setVgrow(normalText, Priority.ALWAYS);
        VBox.setVgrow(normalText1, Priority.ALWAYS);
        this.getStyleClass().add("root");
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/helpwindow/content.css")).toExternalForm());
        this.getChildren().addAll(heading, firstHeading, secondHeading, normalText, secondHeading1, normalText1, secondHeading2, code, firstHeading1,
                secondHeading3, normalText2, code1, secondHeading4, normalText3, code2, secondHeading5, normalText4, code3, secondHeading6, normalText5, code4,
                normalText6, code5, secondHeading7, normalText7, code6, firstHeading2, secondHeading8, normalText8, code7, normalText9, code8,
                secondHeading9, normalText10, code9, secondHeading10, normalText11, secondHeading11, normalText13, code10, firstHeading3, secondHeading13,
                secondHeading12, normalText14, normalText15, secondHeading14, normalText16, secondHeading15, normalText17
        );
    }
}
