<img width="336" alt="Screenshot 2025-03-23 at 3 36 05 PM" src="https://github.com/user-attachments/assets/759ba023-e810-4082-a065-328110b25a9d" />

<img width="336" alt="Screenshot 2025-03-23 at 3 39 45 PM" src="https://github.com/user-attachments/assets/dea88d58-06c8-434f-a269-dbb88e5c1c68" />

## Setup Process

1. **Download the ZIP**  
   - Go to the [GitHub repository](<repository_link_here>).  
   - Click on the **"Code"** button and select **"Download ZIP"**.  
   - Extract the downloaded ZIP file to your desired location.  

2. **Import into Android Studio**  
   - Open **Android Studio**.  
   - Click on **"Open"**.  
   - Navigate to the extracted folder and select it.  
   - Wait for the project to sync and build.  

3. **Run the App**  
   - Connect an emulator or physical device.
   - Run the project from 'main' branch
   - Click on the **Run** button ▶️ to launch the app.  

## Project Architecture

EchoWords follows **Clean Architecture** with the **MVVM** design pattern. The project is structured into three main layers:  

1. **Data Layer**  
   - Handles data operations from **Network (Retrofit)** and **Local Database (Room)**.  

2. **Domain Layer**  
   - Contains repository implementations to abstract data sources.  

3. **UI Layer**  
   - Built with **Jetpack Compose** and consists of **Composables** and **ViewModel** to manage UI state.  

### Tech Stack

- **Background Task Management** → WorkManager  
- **Networking** → Retrofit  
- **Local Storage** → Room  
- **Dependency Injection** → Hilt  
- **Async Operations** → Kotlin Coroutines & Flow  
- **UI Framework** → Jetpack Compose  
- **Lifecycle Management** → ViewModel 

