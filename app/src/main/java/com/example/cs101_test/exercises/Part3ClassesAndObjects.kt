package com.example.cs101_test.exercises

object Part3ClassesAndObjects {
    // OBS!!! PLEASE NOTE THAT FROM NOW ON:
    // IN ALL EXERCISES WITH CLASSES AND INTERFACES IT IS IMPORTANT TO PUT THE ATTRIBUTES IN THE
    //      SAME ORDER AS WRITTEN IN THE EXERCISE DESCRIPTION ELSE IT WILL NOT PASS THE TEST!

    // IN ALL EXERCISES THAT INCLUDES A displayDetails FUNCTION, IT DOES NOT MATTER HOW YOU DISPLAY IT
    // AS LONG AS THE VALUES OF THE VARIABLES ARE INCLUDED IN THE PRINTED TEXT.
    // FOR EXAMPLE, displayDetails OUTPUT IN EXERCISE 1 COULD BE ANY OF THE FOLLOWING:
    // - "the title is CS101 testing, the author Jane Doe, and the price 199.0 DKK"
    // - "title: CS101 testing, author: Jane Doe, price: 199.0"
    // - "CS101 testing, 199.0, Jane Doe"
    // - ETC.

    // ---------------------- EXERCISE 1
    // Create a Book class with 3 attributes: title, author, and price (Double)
    // Add a method to display book details called displayDetails that prints title, author and price
    class Book(val title: String, val author: String, var price: Double) {
        fun displayDetails(){
            println("$title, $author, $price")
        }
    }



    // ---------------------- EXERCISE 2
    // Create a Student class with the attributes name, age, and grades (a list of integers)
    // Add methods to add a grade (addGrade), calculate the average grade (averageGrade as Double),
    // and display student details (displayDetails) which must print name, age and average grade
    class Student(val name: String, var age: Int){
        var grades: MutableList<Int> = mutableListOf()
        fun addGrade(newGrade: Int){
            grades.add(newGrade)
        }
        fun averageGrade(): Double{
            return grades.average()
        }
        fun displayDetails(){
            println("'$name'. '$age'. '${averageGrade()}'.")
        }

    }


    // ---------------------- EXERCISE 3
    // Create a BankAccount class with the attributes accountNumber, balance (Double), and accountHolder
    // Add methods to deposit, withdraw, and display account details (displayDetails) which must include accountNumber, balance, and accountHolder
    // Ensure that the balance cannot be negative
    class BankAccount(val accountNumber: String, var balance: Double = 0.0, var accountHolder: String){
        fun deposit(amount: Double){
            balance+=amount
        }
        fun withdraw(amount: Double){
            if(balance <= 0){
                println("Balance too low")
            } else {
                balance -= amount
            }
        }
        fun displayDetails(){
            println("$accountNumber, $balance, $accountHolder")
        }
    }
        //Kan ikke se hvad min fejl er her


    // ---------------------- EXERCISE 4
    // Create a Product class that includes a name (String) and price (Double)
    // Create a Shop class that contains a list of Product objects called products
    // Add methods to add a product (addProduct), remove a product (removeProduct), and print a list of all the products (listProducts)
    // Include a method to calculate and return the total value of all products in the shop (totalValue)
    // Additionally, create a filterProducts function that takes a minimum price (Double) and returns the products above that price
    class Product(val name: String, var price: Double){

    }

    class Shop(var products: MutableList<Product>){
        fun addProduct(product: Product){
            products.add(product)
        }
        fun removeProduct(product: Product){
            products.remove(product)
        }
        fun listProducts(){
            println(products)
        }
        fun totalValue():Double{
            var calculatedValue: Double = 0.0
            for (product in products){
                calculatedValue += product.price
            }
            return calculatedValue
        }
        fun filterProducts(minimumPrice: Double): List<Product>{
            var filteredProductList: MutableList<Product> = mutableListOf()
            for (product in products){
                if(product.price>minimumPrice){
                    filteredProductList.add(product)
                }
            }
            return filteredProductList
        }
    }
}
