/**
 * Variable to hold timeout in number of milliseconds for server requests
 * Used in function _post in ServerRequest object
 * @type Number
 */
const SERVER_REQUEST_TIMEOUT_MS = 60000;

/**
 * The purpose of this class is to make server calls. One call = one instance.
 * I.e. create object and then make call and do not re-use.
 *
 */
export class ServerCaller {
    constructor() {
        //Flag used to prevent multiple uses of the same instance.
        this._instanceAlreadyUsed = false;
        //Flag if calls are to be logged to console
        this._logToConsole = false;
        //Request arguments
        this._arguments = {};
    }

    /**
     *
     * @param {boolean} enable Optional. True per default.
     * @return {ServerCaller} This for chaining
     */
    enableLogging(enable = true) {
        this._logToConsole = enable;
        return this;
    }

    /**
     * Set the name of the method to call.
     *
     * @param {string} methodName
     * @returns {ServerCaller} This for chaining
     */
    setMethodName(methodName) {
        this._methodName = methodName;
        return this;
    };


    /**
     * Set callback function
     *
     * @param {function} callbackFunction The function to call after a successful
     * response from server.
     * @returns {ServerCaller} This for chaining
     */
    setSuccessCallback(callbackFunction) {
        this._callbackSuccessFunction = callbackFunction;
        return this;
    };


    /**
     * Sets a parameter to pass to callback function. Will be the second
     * parameter after the server response.
     *
     * @param {type} callbackArg
     * @returns {ServerCaller} This for chaining
     */
    setCallbackArg(callbackArg) {
        this._callbackArg = callbackArg;
        return this;
    };

    /**
     *
     *
     * @param {type} callbackFunction
     * @returns {ServerCaller} This for chaining
     */
    setFailCallback(callbackFunction) {
        this._callbackFailFunction = callbackFunction;
        return this;
    };


    /**
     * Add an argument to send to server.
     *
     * @param {string} name The name of the argument.
     * @param {string|object} val The value of the argument.
     * @returns {ServerCaller} This for chaining
     */
    addArgument(name, val) {
        this._arguments[name] = val;
        return this;
    };


    /**
     * Sets arguments to send to server. Overwrites any previsouly set or added
     * argument data.
     *
     * @param {type} args
     * @returns {ServerCaller} This for chaining.
     */
    setArguments(args) {
        this._arguments = args;
        return this;
    };


    /**
     * Makes the call to the server.
     */
    call() {
        if (this._instanceAlreadyUsed) {
            ServerCaller._writeToLog("Only make one call with each instance of ServerCall class.");
        }
        this._instanceAlreadyUsed = true;
        this._getFromServer();
    };


    //*************************************************************************/
    //**    Private Functions
    //*************************************************************************/

    /**
     * Call server for data from methodname
     * @private
     */
    _getFromServer() {
        let url = ServerCaller._getAjaxUrl(this._methodName);
        $.ajax({
            type: "POST",
            url: url,
            data: this._arguments,
            contentType: "application/json; charset=utf-8",
            cache: false,
            timeout: SERVER_REQUEST_TIMEOUT_MS
        })
            .done((response) => {
                ServerCaller._logSuccessfulCall(this._methodName, this._arguments, response);
                response = ServerCaller._toJSON(response);
                this._callbackSuccessFunction(response, this._callbackArg);
            })
            .fail((jqXHR, textStatus, errorThrown) => {
                // failed server response, standard procedure for all calls
                let message = jqXHR.responseText;
                let errorInfo = textStatus + ' ' + errorThrown.message;
                ServerCaller._logFailedCall(this._methodName, this._arguments, message, errorInfo);
                if (this._callbackFailFunction) {
                    message = ServerCaller._toJSON(message);
                    this._callbackFailFunction(message);
                }
            });
    }


    /**
     *
     * @param {String} str
     * @returns {String|Object} String if argument cannot be cast to JSON. JSON
     * if argument string can be cast to JSON.
     * @private
     */
    static _toJSON(str) {
        try {
            return JSON.parse(str);
        } catch (e) {
            return str;
        }
    }


    /**
     *
     * @param {String} methodName
     * @returns {String}
     * @private
     */
    static _getAjaxUrl(methodName) {
        return "//" + document.location.host + "/call/" + methodName;
    }


    /**
     * Log a successful server call.
     *
     * @param {string} methodName
     * @param {type} args
     * @param {string} response
     * @private
     */
    static _logSuccessfulCall(methodName, args, response) {
        let msg = this._formatLogMessage(methodName, args, response);
        this._writeToLog(msg);
    }


    /**
     * Log a failed server call.
     *
     * @param {string} methodName
     * @param {type} args
     * @param {string} response
     * @param {string} errorThrown
     * @private
     */
    static _logFailedCall(methodName, args, response, errorThrown) {
        let msg = this._formatLogMessage(methodName, args, response, errorThrown);
        this._writeToLog(msg);
    }


    /**
     *
     * @param {string} methodName
     * @param {type} args
     * @param {string} response
     * @param {string} auxData Optional. Auxiliary data on the log message.
     * @returns {String}
     * @private
     */
    static _formatLogMessage(methodName, args, response, auxData = '') {
        let msg = "*** Server Call ****";
        msg += "\n\tMethod: '" + methodName + "'";
        if (args) {
            msg += "\n\tArgs: '" + this._toString(args) + "'";
        }
        msg += "\n\tResponse: '" + this._toString(response) + "'";
        if (auxData) {
            msg += "\n\tAux Data: '" + this._toString(auxData) + "'";
        }
        return msg;
    }


    /**
     *
     * @param {string} msg The string to write
     * @private
     */
    static _writeToLog(msg) {
        console.log(msg)
    }


    /**
     *
     * @param {object|string} data
     * @returns {String} The argument data as string.
     * @private
     */
    static _toString(data) {
        let stringMaxLength = 5000;

        //If argument is empty
        if (!data) {
            return "";
        }
        if (typeof data === 'object') {
            data = JSON.stringify(data);
        }

        if (data.length > stringMaxLength) {
            data = data.slice(0, stringMaxLength) + "[...cropped long response here]";
        }
        return data;
    }


}
