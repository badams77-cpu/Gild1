import logo from './logo.svg';
import './App.css';
import GildedComp from './components/GildedComp';
const cors = require('cors')
const corsOption = {
    origin: ['http://localhost:3000'],
    credentials: true,
    methods: ["GET","POST","PUT"]
}


function App() {
  return (
    <div className="App">
      <header className="App-header">
        <GildedComp>

        </GildedComp>
      </header>
    </div>
  );
}

//App.use(cors(corsOption))



export default App;
