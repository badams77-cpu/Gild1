import React,{Component, Button, Form} from "react";
import axios from 'axios'

class GildedComp extends Component {

    constructor() {
        super();
        this.state = {'valid': false,
            'items':[]};
        this.setTime();
    }

    setTime(){
        axios.post("http://localhost:8080/set_now")
            .catch( error => console.log(" Axios error ",error));
    }

    reset(){
        axios.post("http://localhost:8080/reset").then( this.invalidate())
            .catch( error => console.log(" Axios error ",error));;
    }

    invalidate(){
        this.setState( {'items': this.state.items, 'valid': false})
        ;
    }

    nextDay(){
        axios.post("http://localhost:8080/day_passed").then(this.invalidate())
            .catch( error => console.log(" Axios error ",error));;
    }

    getItems(){
        axios.get("http://localhost;8080/inventory").then( res=> {
            var items = res.data;
            this.setState( {'items': items, 'valid': true});
            }
        )
            .catch( error => console.log(" Axios error ",error));;
    }


    render(){
        if (!this.state['valid']){
            this.getItems();
        }
        var items = this.state['items'] || [];
            return (<p><h2>Gilded Rose</h2><p><Form onSubmit={()=>this.reset()}><Button >INITIZATION</Button></Form></p>

            <p><Form onSubmit={()=>this.nextDay()}><Button>NEXT DAY</Button></Form></p>
            <p><table><tr><th>NAME</th><th>Sell In</th><th>Quality</th></tr>
                {
                    items.forEach( i => i.map(
                        item => {
                          return (<tr><td>{item.itemName.name}</td>
                            <td>{item.sellIn}</td>
                        <td>{item.quality}</td></tr>)
                        }))
                }
            </table>
            </p>
            </p>
        );

    }

}

export default GildedComp()