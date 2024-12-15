package com.example.cs101_test

import com.example.cs101_test.exercises.Part3ClassesAndObjects
import org.junit.jupiter.api.Assertions.*
import org.junit.Test
import org.junit.jupiter.api.fail
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class Part3Tests {

    private val clazz = Part3ClassesAndObjects::class.java

    // Helper function to capture stdout
    private fun captureOutput(action: () -> Unit): String {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        val originalOut = System.out
        System.setOut(printStream)
        try {
            action.invoke()
        } finally {
            System.setOut(originalOut)
        }
        return outputStream.toString()
    }

    // ---------------------- EXERCISE 1
    @Test
    fun `exercise 01 testBookClass`() {
        val bookClass = try {
            clazz.declaredClasses.first { it.simpleName == "Book" }
        } catch (e: NoSuchElementException) {
            null
        }

        if (bookClass == null) {
            println("Exercise 1 failed: 'Book' class not found.")
            fail("Exercise 1 failed: 'Book' class not found.")
        }

        try {
            val constructor = bookClass.getConstructor(String::class.java, String::class.java, Double::class.java)
            val bookInstance = constructor.newInstance("CS101 testing", "Ane", 10.0)
            val displayMethod = bookClass.getMethod("displayDetails")

            println("Testing 'Book' class and its methods")
            assertNotNull(bookInstance)
            assertNotNull(displayMethod)

            println("Testing displayDetails()")
            // Capture output and test displayDetails method
            val output = captureOutput {
                displayMethod.invoke(bookInstance)
            }
            assertTrue(output.contains("CS101 testing"), "Expected output to contain 'CS101 testing'.")
            assertTrue(output.contains("Ane"), "Expected output to contain 'Ane'.")
            assertTrue(output.contains("10.0"), "Expected output to contain '10.0'.")

        } catch (e: NoSuchMethodException) {
            println("Exercise 1 failed: Method not found ${e.message?.split("Part3ClassesAndObjects\$Book")?.getOrNull(1) ?: e.message}")
            fail("Exercise 1 failed: Method not found ${e.message?.split("Part3ClassesAndObjects\$Book")?.getOrNull(1) ?: e.message}")
        } catch (e: Exception) {
            println("Exercise 1 failed: ${e.message}")
            fail("Exercise 1 failed: ${e.message}")
        }
    }

    // ---------------------- EXERCISE 2
    @Test
    fun `exercise 02 testCarClass`() {
        val carClass = try {
            clazz.declaredClasses.first { it.simpleName == "Car" }
        } catch (e: NoSuchElementException) {
            null
        }

        if (carClass == null) {
            println("Exercise 2 failed: 'Car' class not found.")
            fail("Exercise 2 failed: 'Car' class not found.")
        }

        try {
            val constructor = carClass.getConstructor(String::class.java, Int::class.java, Boolean::class.java)
            val carInstance = constructor.newInstance("Model1", 2020, false)
            val startMethod = carClass.getMethod("startCar")
            val stopMethod = carClass.getMethod("stopCar")

            println("Testing 'Car' class and its methods")
            assertNotNull(carInstance)
            assertNotNull(startMethod)
            assertNotNull(stopMethod)

            val isRunningField = carClass.getDeclaredField("isRunning")
            isRunningField.isAccessible = true

            println("Testing startCar()")
            // Check if isRunning is true after starting the car
            startMethod.invoke(carInstance)
            var isRunning = isRunningField.getBoolean(carInstance)
            assertTrue(isRunning, "Expected isRunning to be true after starting the car.")

            println("Testing stopCar()")
            // Check if isRunning is false after stopping the car
            stopMethod.invoke(carInstance)
            isRunning = isRunningField.getBoolean(carInstance)
            assertFalse(isRunning, "Expected isRunning to be false after stopping the car.")

        } catch (e: NoSuchMethodException) {
            println("Exercise 2 failed: Method not found ${e.message?.split("Part3ClassesAndObjects\$Car")?.getOrNull(1) ?: e.message}")
            fail("Exercise 2 failed: Method not found ${e.message?.split("Part3ClassesAndObjects\$Car")?.getOrNull(1) ?: e.message}")
        } catch (e: Exception) {
            println("Exercise 2 failed: ${e.message}")
            fail("Exercise 2 failed: ${e.message}")
        }
    }

    // ---------------------- EXERCISE 3
    @Test
    fun `exercise 03 testStudentClass`() {
        val studentClass = try {
            clazz.declaredClasses.first { it.simpleName == "Student" }
        } catch (e: NoSuchElementException) {
            null
        }

        if (studentClass == null) {
            println("Exercise 3 failed: 'Student' class not found.")
            fail("Exercise 3 failed: 'Student' class not found.")
        }

        try {
            val constructor = studentClass.getConstructor(String::class.java, Int::class.java)
            val studentInstance = constructor.newInstance("John Doe", 20)
            val addGradeMethod = studentClass.getMethod("addGrade", Int::class.java)
            val averageGradeMethod = studentClass.getMethod("averageGrade")
            val displayMethod = studentClass.getMethod("displayDetails")

            println("Testing 'Student' class and its methods")
            assertNotNull(studentInstance)
            assertNotNull(addGradeMethod)
            assertNotNull(averageGradeMethod)
            assertNotNull(displayMethod)

            println("Testing addGrade()")
            // Add grades and test average grade calculation
            captureOutput { addGradeMethod.invoke(studentInstance, 90) }
            captureOutput { addGradeMethod.invoke(studentInstance, 80) }
            captureOutput { addGradeMethod.invoke(studentInstance, 70) }

            val averageGrade = averageGradeMethod.invoke(studentInstance) as Double
            println("Testing averageGrade()")
            assertEquals(80.0, averageGrade, 0.001, "Expected average grade to be 80.0.")

            println("Testing displayDetails()")
            // Capture output and test displayDetails method
            val output = captureOutput {
                displayMethod.invoke(studentInstance)
            }
            assertTrue(output.contains("John Doe"), "Expected output to contain 'John Doe'.")
            assertTrue(output.contains("20"), "Expected output to contain '20'.")
            assertTrue(output.contains("80.0"), "Expected output to contain '80.0'.")

        } catch (e: NoSuchMethodException) {
            println("Exercise 3 failed: Method not found ${e.message?.split("Part3ClassesAndObjects\$Student")?.getOrNull(1) ?: e.message}")
            fail("Exercise 3 failed: Method not found ${e.message?.split("Part3ClassesAndObjects\$Student")?.getOrNull(1) ?: e.message}")
        } catch (e: Exception) {
            println("Exercise 3 failed: ${e.message}")
            fail("Exercise 3 failed: ${e.message}")
        }
    }


    // ---------------------- EXERCISE 4
    @Test
    fun `exercise 04 testBankAccountClass`() {
        val bankAccountClass = try {
            clazz.declaredClasses.first { it.simpleName == "BankAccount" }
        } catch (e: NoSuchElementException) {
            null
        }

        if (bankAccountClass == null) {
            println("Exercise 4 failed: 'BankAccount' class not found.")
            fail("Exercise 4 failed: 'BankAccount' class not found.")
        }

        try {
            val constructor = bankAccountClass.getConstructor(String::class.java, Double::class.java, String::class.java)
            val bankAccountInstance = constructor.newInstance("12345", 1000.0, "Alice")
            val depositMethod = bankAccountClass.getMethod("deposit", Double::class.java)
            val withdrawMethod = bankAccountClass.getMethod("withdraw", Double::class.java)
            val displayMethod = bankAccountClass.getMethod("displayDetails")

            // Access balance field
            val balanceField = bankAccountClass.getDeclaredField("balance")
            balanceField.isAccessible = true

            println("Testing 'BankAccount' class and its methods")
            assertNotNull(bankAccountInstance)
            assertNotNull(depositMethod)
            assertNotNull(withdrawMethod)
            assertNotNull(displayMethod)

            // Deposit amount and test balance
            println("Testing deposit method")
            depositMethod.invoke(bankAccountInstance, 500.0)
            var balance = balanceField.getDouble(bankAccountInstance)
            assertEquals(1500.0, balance, 0.001, "Expected balance to be 1500.0 after deposit.")

            // Withdraw amount and test balance
            println("Testing withdraw method")
            withdrawMethod.invoke(bankAccountInstance, 200.0)
            balance = balanceField.getDouble(bankAccountInstance)
            assertEquals(1300.0, balance, 0.001, "Expected balance to be 1300.0 after withdrawal.")

            // Test insufficient funds scenario
            println("Testing insufficient funds method")
            withdrawMethod.invoke(bankAccountInstance, 2000.0)
            balance = balanceField.getDouble(bankAccountInstance)
            assertEquals(1300.0, balance, 0.001, "Expected balance to remain 1300.0 after failed withdrawal due to insufficient funds.")

            // Capture output and test displayDetails method
            val output = captureOutput { displayMethod.invoke(bankAccountInstance) }
            assertTrue(output.contains("12345"), "Exercise 3 failed: displayDetails did not print all details.")
            assertTrue(output.contains("1300"), "Exercise 3 failed: displayDetails did not print all details.")
            assertTrue(output.contains("Alice"), "Exercise 3 failed: displayDetails did not print all details.")

        } catch (e: NoSuchMethodException) {
            println("Exercise 4 failed: Method not found ${e.message?.split("Part3ClassesAndObjects\$BankAccount")?.getOrNull(1) ?: e.message}")
            fail("Exercise 4 failed: Method not found ${e.message?.split("Part3ClassesAndObjects\$BankAccount")?.getOrNull(1) ?: e.message}")
        } catch (e: Exception) {
            println("Exercise 4 failed: ${e.message}")
            fail("Exercise 4 failed: ${e.message}")
        }
    }

    // ---------------------- EXERCISE 5
    @Test
    fun `exercise 05 testLibraryClass`() {
        val libraryClass = try {
            clazz.declaredClasses.first { it.simpleName == "Library" }
        } catch (e: NoSuchElementException) {
            null
        }

        if (libraryClass == null) {
            println("Exercise 5 failed: 'Library' class not found.")
            fail("Exercise 5 failed: 'Library' class not found.")
        }

        val bookClass = try {
            clazz.declaredClasses.first { it.simpleName == "Book" }
        } catch (e: NoSuchElementException) {
            null
        }

        if (bookClass == null) {
            println("Exercise 5 failed: 'Book' class not found.")
            fail("Exercise 5 failed: 'Book' class not found.")
        }

        try {
            val libraryConstructor = libraryClass.getConstructor()
            val libraryInstance = libraryConstructor.newInstance()
            val addBookMethod = libraryClass.getMethod("addBook", bookClass)
            val removeBookMethod = libraryClass.getMethod("removeBook", bookClass)
            val listBooksMethod = libraryClass.getMethod("listBooks")

            val bookConstructor = bookClass.getConstructor(String::class.java, String::class.java, Double::class.java)
            val bookInstance = bookConstructor.newInstance("Title1", "Author1", 10.0)

            println("Testing 'Library' class and its methods")
            assertNotNull(libraryInstance)
            assertNotNull(addBookMethod)
            assertNotNull(removeBookMethod)
            assertNotNull(listBooksMethod)
            assertNotNull(bookInstance)

            println("Testing addBook()")
            // Add book and check the list of books
            addBookMethod.invoke(libraryInstance, bookInstance)
            val booksField = libraryClass.getDeclaredField("books")
            booksField.isAccessible = true
            var booksList = booksField.get(libraryInstance) as List<*>
            assertTrue(booksList.contains(bookInstance), "Expected books list to contain the added book.")

            println("Testing removeBook()")
            // Remove book and check the list of books
            removeBookMethod.invoke(libraryInstance, bookInstance)
            booksList = booksField.get(libraryInstance) as List<*>
            assertFalse(booksList.contains(bookInstance), "Expected books list to not contain the removed book.")

            println("Testing listBooks()")
            // Test listBooks method directly
            val listBooksOutput = captureOutput {
                listBooksMethod.invoke(libraryInstance)
            }
            assertTrue(listBooksOutput.contains("Books in library:"), "Expected output to contain 'Books in library:'.")

        } catch (e: NoSuchMethodException) {
            println("Exercise 5 failed: Method not found ${e.message?.split("Library")?.getOrNull(1) ?: e.message}")
            fail("Exercise 5 failed: Method not found ${e.message?.split("Library")?.getOrNull(1) ?: e.message}")
        } catch (e: Exception) {
            println("Exercise 5 failed: ${e.message}")
            fail("Exercise 5 failed: ${e.message}")
        }
    }


    // ---------------------- EXERCISE 6
    @Test
    fun `exercise 06 testSmartphoneClass`() {
        val smartphoneClass = try {
            clazz.declaredClasses.first { it.simpleName == "Smartphone" }
        } catch (e: NoSuchElementException) {
            null
        }

        if (smartphoneClass == null) {
            println("Exercise 6 failed: 'Smartphone' class not found.")
            fail("Exercise 6 failed: 'Smartphone' class not found.")
        }

        try {
            val constructor = smartphoneClass.getConstructor(String::class.java, String::class.java)
            val smartphoneInstance = constructor.newInstance("Brand1", "Model1")
            val turnOnMethod = smartphoneClass.getMethod("turnOn")
            val turnOffMethod = smartphoneClass.getMethod("turnOff")
            val chargeBatteryMethod = smartphoneClass.getMethod("chargeBattery")
            val displayMethod = smartphoneClass.getMethod("displayDetails")

            println("Testing 'Smartphone' class and its methods")
            assertNotNull(smartphoneInstance)
            assertNotNull(turnOnMethod)
            assertNotNull(turnOffMethod)
            assertNotNull(chargeBatteryMethod)
            assertNotNull(displayMethod)

            println("Testing turnOn()")
            // Test turnOn method
            turnOnMethod.invoke(smartphoneInstance)
            val isOnField = smartphoneClass.getDeclaredField("isOn")
            isOnField.isAccessible = true
            var isOn = isOnField.getBoolean(smartphoneInstance)
            assertTrue(isOn, "Expected isOn to be true after turning on the phone.")

            println("Testing turnOff()")
            // Test turnOff method
            turnOffMethod.invoke(smartphoneInstance)
            isOn = isOnField.getBoolean(smartphoneInstance)
            assertFalse(isOn, "Expected isOn to be false after turning off the phone.")

            println("Testing turnOn() doublecheck")
            // Test turnOn method
            turnOnMethod.invoke(smartphoneInstance)
            isOn = isOnField.getBoolean(smartphoneInstance)
            assertTrue(isOn, "Expected isOn to be true after turning on the phone.")

            println("Testing chargeBattery()")
            // Test chargeBattery method
            chargeBatteryMethod.invoke(smartphoneInstance)
            val batteryLevelField = smartphoneClass.getDeclaredField("batteryLevel")
            batteryLevelField.isAccessible = true
            val batteryLevel = batteryLevelField.getInt(smartphoneInstance)
            assertEquals(100, batteryLevel, "Expected battery level to be 100 after charging.")

            println("Testing displayDetails()")
            // Capture output and test displayDetails method
            val output = captureOutput {
                displayMethod.invoke(smartphoneInstance)
            }
            assertTrue(output.contains("Brand: Brand1"), "Expected output to contain 'Brand: Brand1'.")
            assertTrue(output.contains("Model: Model1"), "Expected output to contain 'Model: Model1'.")
            assertTrue(output.contains("Battery Level: 100%"), "Expected output to contain 'Battery Level: 100%'.")
            assertTrue(output.contains("Is On: true"), "Expected output to contain 'Is On: true'.")

        } catch (e: NoSuchMethodException) {
            println("Exercise 6 failed: Method not found ${e.message?.split("Smartphone")?.getOrNull(1) ?: e.message}")
            fail("Exercise 6 failed: Method not found ${e.message?.split("Smartphone")?.getOrNull(1) ?: e.message}")
        } catch (e: Exception) {
            println("Exercise 6 failed: ${e.message}")
            fail("Exercise 6 failed: ${e.message}")
        }
    }

    // ---------------------- EXERCISE 7
    @Test
    fun `exercise 07 testShopClass`() {
        val productClass = try {
            clazz.declaredClasses.first { it.simpleName == "Product" }
        } catch (e: NoSuchElementException) {
            null
        }

        if (productClass == null) {
            println("Exercise 7 failed: 'Product' class not found.")
            fail("Exercise 7 failed: 'Product' class not found.")
        }

        val shopClass = try {
            clazz.declaredClasses.first { it.simpleName == "Shop" }
        } catch (e: NoSuchElementException) {
            null
        }

        if (shopClass == null) {
            println("Exercise 7 failed: 'Shop' class not found.")
            fail("Exercise 7 failed: 'Shop' class not found.")
        }

        try {
            val productConstructor = productClass.getConstructor(String::class.java, Double::class.java)
            val productInstance1 = productConstructor.newInstance("Product1", 20.0)
            val productInstance2 = productConstructor.newInstance("Product2", 30.0)
            val productInstance3 = productConstructor.newInstance("Product3", 40.0)
            val shopConstructor = shopClass.getConstructor()
            val shopInstance = shopConstructor.newInstance()
            val addProductMethod = shopClass.getMethod("addProduct", productClass)
            val removeProductMethod = shopClass.getMethod("removeProduct", productClass)
            val listProductsMethod = shopClass.getMethod("listProducts")
            val totalValueMethod = shopClass.getMethod("totalValue")
            val filterProductsMethod = shopClass.getMethod("filterProducts", Double::class.java)

            println("Testing 'Shop' class and its methods")
            assertNotNull(productInstance1)
            assertNotNull(productInstance2)
            assertNotNull(productInstance3)
            assertNotNull(shopInstance)
            assertNotNull(addProductMethod)
            assertNotNull(removeProductMethod)
            assertNotNull(listProductsMethod)
            assertNotNull(totalValueMethod)
            assertNotNull(filterProductsMethod)

            println("Testing addProduct()")
            // Add products and check the list of products
            addProductMethod.invoke(shopInstance, productInstance1)
            addProductMethod.invoke(shopInstance, productInstance2)
            addProductMethod.invoke(shopInstance, productInstance3)
            val productsField = shopClass.getDeclaredField("products")
            productsField.isAccessible = true
            var productsList = productsField.get(shopInstance) as List<*>
            assertTrue(productsList.contains(productInstance1), "Expected products list to contain 'Product1'.")
            assertTrue(productsList.contains(productInstance2), "Expected products list to contain 'Product2'.")
            assertTrue(productsList.contains(productInstance3), "Expected products list to contain 'Product3'.")

            println("Testing removeProduct()")
            // Remove product and check the list of products
            removeProductMethod.invoke(shopInstance, productInstance2)
            productsList = productsField.get(shopInstance) as List<*>
            assertFalse(productsList.contains(productInstance2), "Expected products list to not contain 'Product2' after removal.")

            println("Testing listProducts()")
            // Test listProducts method
            val listProductsOutput = captureOutput {
                listProductsMethod.invoke(shopInstance)
            }
            println(listProductsOutput)
            assertTrue(listProductsOutput.contains("Product1"), "Expected output to contain 'Product1'.")
            assertTrue(listProductsOutput.contains("20.0"), "Expected output to contain '20.0'.")
            assertTrue(listProductsOutput.contains("Product3"), "Expected output to contain 'Product3'.")
            assertTrue(listProductsOutput.contains("40.0"), "Expected output to contain '40.0'.")

            println("Testing totalValue()")
            // Test totalValue method
            val totalValue = totalValueMethod.invoke(shopInstance) as Double
            assertEquals(60.0, totalValue, 0.001, "Expected total value to be 60.0 after removal of one product.")

            println("Testing filterProducts()")
            // Test filterProducts method
            val filteredProducts = filterProductsMethod.invoke(shopInstance, 25.0) as List<*>
            assertFalse(filteredProducts.contains(productInstance1), "Expected filtered products to not contain 'Product1'.")
            assertTrue(filteredProducts.contains(productInstance3), "Expected filtered products to contain 'Product3'.")
            assertEquals(1, filteredProducts.size, "Expected one product in the filtered list.")

        } catch (e: NoSuchMethodException) {
            println("Exercise 7 failed: Method not found ${e.message?.split("Shop")?.getOrNull(1) ?: e.message}")
            fail("Exercise 7 failed: Method not found ${e.message?.split("Shop")?.getOrNull(1) ?: e.message}")
        } catch (e: Exception) {
            println("Exercise 7 failed: ${e.message}")
            fail("Exercise 7 failed: ${e.message}")
        }
    }
}
