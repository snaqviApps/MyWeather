//package preliminary.myweatheroverview.data.remote.service
//
//import android.content.Context
//import androidx.work.CoroutineWorker
//import androidx.work.WorkerParameters
//import androidx.work.workDataOf
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//
//class WeatherWorkManager(
//    private val appContext : Context,
//    private val workerParams : WorkerParameters
//) : CoroutineWorker(appContext, workerParams) {
//    override suspend fun doWork(): Result {
//
////        TODO("Not yet implemented")
//        withContext(Dispatchers.IO) {
//
//            Result.success (
//                // Use States here to update the UI-State in Composable
////                workDataOf("work_status" to)
//                println("Inside doWork")
//            )
//        }
//    }
//
//}