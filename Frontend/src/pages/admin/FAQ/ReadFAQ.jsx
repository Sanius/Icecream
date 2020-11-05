import React from "react";
import {Link} from "react-router-dom";

import baseUrl from "baseUrl.js";
import authHeader from "services/authHeader.js";
import FAQTuple from "components/tuples/FAQTuple.jsx";
import Pagination from "components/pagination/Pagination.jsx";

class ReadFAQ extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            faqs: [],
            totalRecords: null,
            currentPage: null,
            totalPages: null,
            pageLimit: 5
        };
    }

    componentDidMount() {
      this.getData();
    }

    getData = async() => {
        await baseUrl.get(`/faq?number=0&size=${this.state.pageLimit}`, {headers: authHeader()})
        .then( (response) => {  
            this.setState({
                faqs: Object.values(response.data)[0],
                totalRecords: parseInt(Object.keys(response.data)[0])
            })
        })
        .catch( (error) => {
            console.log(error);
        });
    }

    renderTuples = () => {
        return this.state.faqs.map( (value, index) => {
            return <FAQTuple obj={value} key={index}/>
        });
    }

    onPageChanged = data => {
        const { currentPage, pageLimit } = data;
        this.setState({ currentPage, pageLimit });
        baseUrl.get(`/faq?number=${currentPage-1}&size=${pageLimit}`, {headers: authHeader()})
        .then( (response) => {
            this.setState({
                faqs: Object.values(response.data)[0],
                totalRecords: parseInt(Object.keys(response.data)[0])
            })
        })
        .catch( (error) => {
            console.log(error);
        });
    } 
    
    onHandlePageLimitSelection = async (e) => {
        e.preventDefault();
        this.setState({pageLimit: parseInt(e.target.value)});
    }

    render() {
        const { totalRecords, pageLimit } = this.state;
        return (
<main>
    <div className="container">
        
        <div className="container text-center">
            <div>
                <h1 className="h1-view">FAQ</h1>
            </div>
            <p>
                <input
                    type="text"
                    placeholder="Search FAQ by question..."
                    name="searchtext"
                />
            </p>
            <p>
                <button
                    id="search button"
                    type="submit"
                    className="btn btn-primary"
                >
                    Search
                </button>
            </p>

            <div className="d-flex flex-row align-items-center">
                Page size:
                <select defaultValue={pageLimit} onChange={this.onHandlePageLimitSelection}>
                    <option value="5">5</option>
                    <option value="10">10</option>
                    <option value="15">15</option>
                </select>
            </div><br/>
            <div className="d-flex table-data">
                <table className="table table-striped scrollTable center"
                    border={1}
                    cellSpacing={1}
                >
                    <thead className="thead-dark">
                        <tr>
                            {/* <th>select</th> */}
                            <th>ID</th>
                            <th>Question</th>
                            <th>Answer</th>
                            <th>Last modified date</th>
                            <th colSpan="2">Action</th>
                        </tr>
                    </thead>
                    <tbody id="tbody">
                        {this.renderTuples()}
                    </tbody>
                </table>
            </div>
            
            <div className="d-flex flex-row py-4 align-items-center">
               {
                   (totalRecords !== null) ?
                   <Pagination 
                   totalRecords={totalRecords} 
                   pageLimit={pageLimit} 
                   pageNeighbours={1} 
                   onPageChanged={this.onPageChanged}
                    /> : null
               }
            </div>

            <div className="btn">
                <Link className="btn btn-primary" to="/admin/faq/create">
                    Create new FAQ
                </Link>
            </div>
          
        </div>
    </div>
</main>
        );
    }
}

export default ReadFAQ;
