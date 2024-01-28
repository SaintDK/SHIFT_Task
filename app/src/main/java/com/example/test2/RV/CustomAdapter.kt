import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shift_tt.db.ItemDetails
import com.example.test2.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CustomAdapter(private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private val data = mutableListOf<String>() // Ваш список данных

    // Добавить метод для вставки новых данных в адаптер
    fun addData(item: String) {
        data.add(item)
        notifyDataSetChanged() // Обновить RecyclerView
    }

    fun setData(newData: Flow<List<ItemDetails>>) {
        newData.map { list ->
            list.map { entityItem ->
                // преобразовать EntityItem в строковый формат
                entityItem.toString()
            }
        }.onEach { newDataList ->
            // Обновляем данные в главном потоке
            GlobalScope.launch(Dispatchers.Main) {
                data.clear()
                data.addAll(newDataList)
                notifyDataSetChanged() // Обновляем RecyclerView
            }
        }.launchIn(GlobalScope)
    }

    // Создаем новые views (вызывается layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_layout, viewGroup, false)
        return ViewHolder(view, onItemClick)
    }

    // Заменяет контент отдельного view (вызывается layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = data[position]
    }

    // Возвращает размер DataSet (вызывается layout manager)
    override fun getItemCount(): Int {
        return data.size
    }

    // Предоставляет ссылку на представления для каждого элемента
    class ViewHolder(view: View, onItemClick: (String) -> Unit) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)

        init {
            view.setOnClickListener {
                onItemClick(textView.text.toString())
            }
        }
    }
}
