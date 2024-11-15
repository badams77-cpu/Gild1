import React,{Component, Button, Form} from "react";
import http from '../http-common'

class GildedComp extends Component {

    constructor() {
        super();
        this.state = {'valid': false,
            'items':[]};
        this.setTime();
    }

    setTime(){
        http.post("set_now")
            .catch( error => console.log(" Axios error ",error));
    }

    reset(){
        http.post("reset").then( this.invalidate())
            .catch( error => console.log(" Axios error ",error));;
    }

    invalidate(){
        this.setState( {'items': this.state.items, 'valid': false})
        ;
    }

    nextDay(){
        http.post("day_passed").then(this.invalidate())
            .catch( error => console.log(" Axios error ",error));;
    }

    getItems(){
        http.get("inventory").then( res=> {
            var items = res.data;
            this.setState( {'items': items, 'valid': true});
            console.log("Got items: ", items);
            }
        )
            .catch( error => console.log(" Axios error ",error));;
    }


    render(){
        if (!this.state['valid']){
            this.getItems();
        }
        var items = this.state['items'] || [];
            return (<div><h2>Gilded Rose</h2>
                    <form onSubmit={()=>this.reset()}><button>INITIZATION</button></form>

            <form onSubmit={()=>this.nextDay()}><button>NEXT DAY</button></form>
            <div><table><tbody><tr><th>NAME</th><th>Sell In</th><th>Quality</th></tr>
                {
                    items.map( item => item==null ? '' : (<tr><td>{item.itemName}</td>
                            <td>{item.sellIn}</td>
                        <td>{item.quality}</td></tr>)
                        )
                }
            </tbody></table>
            </div>
            </div>
        );

    }

}

export default GildedComp