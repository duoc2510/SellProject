package com.sellproject

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.sellproject.data.Food

class FoodListFragment : Fragment(R.layout.fragment_food_list) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var foodList: ArrayList<Food>
    private lateinit var database: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvFoods)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3) // 2 cột ngang
        foodList = arrayListOf()

        database = FirebaseDatabase.getInstance().getReference("foods")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                foodList.clear()
                for (foodSnap in snapshot.children) {
                    val food = foodSnap.getValue(Food::class.java)
                    food?.let { foodList.add(it) }
                }
                recyclerView.adapter = FoodAdapter(foodList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Lỗi tải dữ liệu", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
