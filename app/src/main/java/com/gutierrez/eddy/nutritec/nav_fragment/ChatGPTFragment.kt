package com.gutierrez.eddy.nutritec.nav_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gutierrez.eddy.nutritec.adapter.ChatAdapter
import com.gutierrez.eddy.nutritec.databinding.FragmentChatgptBinding
import com.gutierrez.eddy.nutritec.models.Message

class ChatGPTFragment : Fragment() {

    private lateinit var viewModel: ChatViewModel
    private var _binding: FragmentChatgptBinding? = null
    private val binding get() = _binding!!

    private val messages = mutableListOf<Message>()
    private lateinit var adapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatgptBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        adapter = ChatAdapter(messages)
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.chatRecyclerView.adapter = adapter

        binding.sendButton.setOnClickListener {
            val message = binding.messageEditText.text.toString().trim()
            if (message.isNotEmpty()) {
                Log.d("ChatGPTFragment", "Sending message: $message")
                messages.add(Message(message, true))
                adapter.notifyItemInserted(messages.size - 1)
                binding.chatRecyclerView.scrollToPosition(messages.size - 1)
                binding.messageEditText.text.clear()

                viewModel.sendMessage(message) { response ->
                    response?.let {
                        Log.d("ChatGPTFragment", "Received response: $it")
                        messages.add(Message(it, false))
                        adapter.notifyItemInserted(messages.size - 1)
                        binding.chatRecyclerView.scrollToPosition(messages.size - 1)
                    } ?: run {
                        Log.e("ChatGPTFragment", "Error processing request")
                        messages.add(Message("Error al procesar la solicitud", false))
                        adapter.notifyItemInserted(messages.size - 1)
                        binding.chatRecyclerView.scrollToPosition(messages.size - 1)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
